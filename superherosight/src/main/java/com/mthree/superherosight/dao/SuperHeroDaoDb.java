/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dao.LocationDaoDb.LocationMapper;
import com.mthree.superherosight.dao.OrganizationDaoDb.OrganizationMapper;
import com.mthree.superherosight.dao.SuperPowerDaoDb.SuperPowerMapper;
import com.mthree.superherosight.dto.Location;
import com.mthree.superherosight.dto.Organizations;
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author salon
 */
@Repository
public class SuperHeroDaoDb implements SuperHeroDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    
    
    

    
    
    public static final class SuperHeroMapper implements RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
            SuperHero superHero = new SuperHero();
            superHero.setHeroId(rs.getInt("heroId"));
            superHero.setHeroName(rs.getString("heroName"));
            superHero.setHeroDescription(rs.getString("heroDescription"));
            return superHero;
        }
    }


    @Override
    public SuperHero getSuperHeroById(int id) {
        try{
            final String SELECT_HERO_BY_ID = "SELECT * FROM superHero WHERE heroId = ?";
            SuperHero superHero = jdbc.queryForObject(SELECT_HERO_BY_ID, new SuperHeroMapper(), id);
            superHero.setSuperPower(getSuperPowerForSuperHero(id));
            superHero.setOrganizations(getOrganizationsForSuperHero(id));
            superHero.setLocation(getLocationsForSuperHero(id));
            return superHero;    
        }catch(DataAccessException ex) {
            return null;
        }
    }
    
    public SuperPower getSuperPowerForSuperHero(int id){
        final String SELECT_SUPERPOWER_FOR_HERO  = "SELECT sp.* FROM superPower sp JOIN superHero sh ON sh.powerId = sp.powerId WHERE sh.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_HERO, new SuperPowerMapper(), id);
    }
    
    public List<Organizations> getOrganizationsForSuperHero(int id){
        final String SELECT_ORGANIZATIONS_FOR_HERO = "SELECT o.* FROM organizations o JOIN hero_organization ho ON ho.orgId = o.orgId WHERE ho.heroId = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_HERO, new OrganizationMapper(), id);
    }
    
    public List<Location> getLocationsForSuperHero(int id){
        final String SELECT_LOCATIONS_FOR_HERO = "SELECT l.* FROM location l JOIN hero_location hl ON hl.locationId = l.locationId WHERE hl.heroId = ?";
        return jdbc.query(SELECT_LOCATIONS_FOR_HERO, new LocationMapper(), id);
        
    }
                        
    @Override
    public List<SuperHero> getAllSuperHeros() {
        final String SELECT_ALL_SUPERHERO = "SELECT * FROM superHero";
        List<SuperHero> superHeros = jdbc.query(SELECT_ALL_SUPERHERO,new SuperHeroMapper());
        associateSuperpowerAndOrganizationsAndLocations(superHeros);
        return superHeros;
    }
    
    public void associateSuperpowerAndOrganizationsAndLocations(List<SuperHero> superHeros){
        for(SuperHero superHero : superHeros){
            superHero.setSuperPower(getSuperPowerForSuperHero(superHero.getHeroId()));
            superHero.setOrganizations(getOrganizationsForSuperHero(superHero.getHeroId()));
            superHero.setLocation(getLocationsForSuperHero(superHero.getHeroId()));
        }
    }

    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero superHero) {
        final String INSERT_SUPERHERO = "INSERT INTO superHero(heroName, heroDescription, powerId) VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superHero.getHeroName(),
                superHero.getHeroDescription(),
                superHero.getSuperPower().getPowerId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superHero.setHeroId(newId);
        insertHeroOrganization(superHero);
        insertHeroLocation(superHero);
        return superHero;
    }
    
    private void insertHeroOrganization(SuperHero superHero){
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_organization(heroId, orgId) VALUES(?,?)";
        for(Organizations organization:superHero.getOrganizations()){
            jdbc.update(INSERT_HERO_ORGANIZATION,
                    superHero.getHeroId(),
                    organization.getOrgId());
        }
    }
    
    private void insertHeroLocation(SuperHero superHero){
        final String INSERT_HERO_LOCATION = "INSERT INTO hero_location(heroId, locationId) VALUES(?,?)";
        for(Location location:superHero.getLocation()){
            jdbc.update(INSERT_HERO_LOCATION,
                    superHero.getHeroId(),
                    location.getLocationId());
        }
    }
    
    

    @Override
    @Transactional
    public void updateSuperHero(SuperHero superHero) {
        final String UPDATE_SUPERHERO = "UPDATE superHero SET heroName = ?, heroDescription = ?, powerId = ? WHERE heroId = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superHero.getHeroName(),
                superHero.getHeroDescription(),
                superHero.getSuperPower().getPowerId(),
                superHero.getHeroId());
        
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, superHero.getHeroId());
        insertHeroOrganization(superHero);
        
        //-----------------------------think on it
        final String DELETE_HEROSIGHT="DELETE FROM herosight WHERE heroId= ?";
        jdbc.update(DELETE_HEROSIGHT,superHero.getHeroId());
        
        final String DELETE_HERO_LOCATION = "DELETE FROM hero_location WHERE heroId = ?";
        jdbc.update(DELETE_HERO_LOCATION, superHero.getHeroId());
        insertHeroLocation(superHero); 
    }

    @Override
    @Transactional
    public void deleteSuperHeroById(int id) {
        final String DELETE_HEROSIGHT="DELETE FROM herosight WHERE heroId= ?";
        jdbc.update(DELETE_HEROSIGHT, id);
        
        final String DELETE_HERO_LOCATION = "DELETE FROM hero_location WHERE heroId = ?";
        jdbc.update(DELETE_HERO_LOCATION, id);
        
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        
        final String DELETE_HERO = "DELETE FROM superHero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<SuperHero> getSuperHerosForSuperPower(SuperPower superPower) {
        final String SELECT_SUPERHEROS_FOR_SUPERPOWER = "SELECT * FROM superHero WHERE powerId = ?";
        List<SuperHero> superHeros = jdbc.query(SELECT_SUPERHEROS_FOR_SUPERPOWER, new SuperHeroMapper(),superPower.getPowerId());
        associateSuperpowerAndOrganizationsAndLocations(superHeros);
        return superHeros;
    }

    @Override
    public List<SuperHero> getSuperHerosForOrganization(Organizations organization) {
        final String SELECT_SUPERHEROS_FOR_ORGANIZATION = "SELECT sh.* FROM superHero sh JOIN hero_organization ho ON ho.heroId = sh.heroId WHERE ho.orgId = ?";
        List<SuperHero> superHeros = jdbc.query(SELECT_SUPERHEROS_FOR_ORGANIZATION, new SuperHeroMapper(), organization.getOrgId());
        associateSuperpowerAndOrganizationsAndLocations(superHeros);
        return superHeros;            
    }
    
    @Override
    public List<SuperHero> getSuperHerosForLocation(Location location) {
        final String SELECT_SUPERHEROS_FOR_LOCATION = "SELECT sh.* FROM superHero sh JOIN hero_location hl ON hl.heroId = sh.heroId WHERE hl.locationId = ?";
        List<SuperHero> superHeros = jdbc.query(SELECT_SUPERHEROS_FOR_LOCATION, new SuperHeroMapper(), location.getLocationId());
        associateSuperpowerAndOrganizationsAndLocations(superHeros);
        return superHeros; 
            
    }        
}
