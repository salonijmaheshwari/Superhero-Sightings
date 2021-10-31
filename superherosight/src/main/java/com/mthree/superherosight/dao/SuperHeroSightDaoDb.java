/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dao.LocationDaoDb.LocationMapper;
import com.mthree.superherosight.dao.SuperHeroDaoDb.SuperHeroMapper;
import com.mthree.superherosight.dto.Location;
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperHeroSight;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class SuperHeroSightDaoDb implements SuperHeroSightDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    SuperHeroDaoDb superHeroDaoDb;

    public static final class SuperHeroSightMapper implements RowMapper<SuperHeroSight> {

        @Override
        public SuperHeroSight mapRow(ResultSet rs, int index) throws SQLException {
            SuperHeroSight superHeroSight = new SuperHeroSight();
            superHeroSight.setSightId(rs.getInt("sightId"));
            superHeroSight.setSightDate(rs.getDate("sightDate").toLocalDate());
            return superHeroSight;
        }
    }

    @Override
    public SuperHeroSight getSuperHeroSightById(int id) {
        try {
            final String SELECT_SUPERHEROSIGHT_BY_ID = "SELECT * FROM heroSight WHERE sightId = ?";
            SuperHeroSight superHeroSight = jdbc.queryForObject(SELECT_SUPERHEROSIGHT_BY_ID, new SuperHeroSightMapper(), id);
            superHeroSight.setSuperHero(getSuperHeroForSuperHeroSight(id));
            superHeroSight.setLocation(getLocationForSuperHeroSight(id));
            return superHeroSight;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private SuperHero getSuperHeroForSuperHeroSight(int id) {
        final String SELECT_SUPERHERO_FOR_SUPERHEROSIGHT = "SELECT sh.* FROM superHero sh JOIN\n"
                + "	hero_location hl ON sh.heroId = hl.heroId JOIN\n"
                + "    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN\n"
                + "    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId where sightId = ?";
        SuperHero superHero = jdbc.queryForObject(SELECT_SUPERHERO_FOR_SUPERHEROSIGHT, new SuperHeroMapper(), id);
        superHero.setSuperPower(superHeroDaoDb.getSuperPowerForSuperHero(superHero.getHeroId()));
        superHero.setLocation(superHeroDaoDb.getLocationsForSuperHero(superHero.getHeroId()));
        superHero.setOrganizations(superHeroDaoDb.getOrganizationsForSuperHero(superHero.getHeroId()));

        return superHero;
    }

    private Location getLocationForSuperHeroSight(int id) {
        final String SELECT_LOCATION_FOR_SUPERHEROSIGHT = "SELECT l.* FROM superHero sh JOIN\n"
                + "	hero_location hl ON sh.heroId = hl.heroId JOIN\n"
                + "    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN\n"
                + "    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId where sightId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SUPERHEROSIGHT, new LocationMapper(), id);
    }

    @Override
    public List<SuperHeroSight> getAllSuperHeroSights() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM heroSight";
        List<SuperHeroSight> sights = jdbc.query(SELECT_ALL_SIGHTINGS, new SuperHeroSightMapper());
        associateSuperHeroAndLocation(sights);
        return sights;
    }

    private void associateSuperHeroAndLocation(List<SuperHeroSight> sights) {
        for (SuperHeroSight sight : sights) {
            sight.setSuperHero(getSuperHeroForSuperHeroSight(sight.getSightId()));
            sight.setLocation(getLocationForSuperHeroSight(sight.getSightId()));
        }
    }

    @Override
    @Transactional
    public SuperHeroSight addSuperHeroSight(SuperHeroSight superHeroSight) {
        final String INSERT_SIGHTING = "INSERT INTO heroSight(sightDate,heroId,locationId) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                superHeroSight.getSightDate(),
                superHeroSight.getSuperHero().getHeroId(),
                superHeroSight.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superHeroSight.setSightId(newId);

        return superHeroSight;
    }

    @Override
    public void updateSuperHeroSight(SuperHeroSight superHeroSight) {
        final String UPDATE_SIGHTING = "UPDATE herosight SET sightDate = ?, heroId = ?, locationId = ? WHERE sightId = ?";
        jdbc.update(UPDATE_SIGHTING,
                superHeroSight.getSightDate(),
                superHeroSight.getSuperHero().getHeroId(),
                superHeroSight.getLocation().getLocationId(),
                superHeroSight.getSightId());
    }

    @Override
    public void deleteSuperHeroSightById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM herosight WHERE sightId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<SuperHeroSight> getAllSuperHeroSightsForADate(LocalDate date) {

        final String SELECT_ALL_SIGHTINGS_FOR_A_DATE = "select * from herosight WHERE sightDate='" + date + "'";
        List<SuperHeroSight> sights = jdbc.query(SELECT_ALL_SIGHTINGS_FOR_A_DATE, new SuperHeroSightMapper());
        associateSuperHeroAndLocation(sights);
        return sights;
    }

    @Override
    public List<SuperHeroSight> getLatestTenSightings() {
        final String SELECT_LATEST_SIGHTINGS = "SELECT hs.sightId,sh.heroName,l.locationName,hs.sightDate FROM superHero sh JOIN\n"
                + "	hero_location hl ON sh.heroId = hl.heroId JOIN\n"
                + "    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN\n"
                + "    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId\n"
                + "    ORDER BY sightId DESC LIMIT 10";

        List<SuperHeroSight> latestsights = jdbc.query(SELECT_LATEST_SIGHTINGS, new SuperHeroSightMapper());
        associateSuperHeroAndLocation(latestsights);
        return latestsights;
    }

}
