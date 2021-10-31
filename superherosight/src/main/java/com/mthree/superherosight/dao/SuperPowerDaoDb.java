/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

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
public class SuperPowerDaoDb implements SuperPowerDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
            SuperPower sp = new SuperPower();
            sp.setPowerId(rs.getInt("powerId"));
            sp.setSuperPowerName(rs.getString("superPowerName"));
            
            return sp;
        }
    }

    @Override
    public SuperPower getSuperPowerById(int id) {
        try{
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superPower WHERE powerId = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperPowerMapper(), id);
        }catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM superPower";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperPowerMapper());
    }

    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superPower(superPowerName) VALUES(?)";
        jdbc.update(INSERT_SUPERPOWER, superPower.getSuperPowerName());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setPowerId(newId);
        return superPower;
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        final String UPDATE_SUPERPOWER = "UPDATE superPower SET superPowerName = ? WHERE powerId = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superPower.getSuperPowerName(),
                superPower.getPowerId());
    }

    @Override
    @Transactional
    public void deleteSuperPowerById(int id) {
        
        final String DELETE_HERO_SIGHT = "DELETE hs.* FROM herosight hs "
                + "JOIN hero_location hl ON hs.heroId=hl.heroId "
                + "JOIN superHero sh ON hl.heroId = sh.heroId WHERE sh.powerId = ?";
        jdbc.update(DELETE_HERO_SIGHT, id);
          
        final String DELETE_HERO_ORGANIZATION = "DELETE ho.* FROM hero_organization ho "
                + "JOIN superHero sh ON ho.heroId = sh.heroId WHERE sh.powerId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        
        final String DELETE_HERO_LOCATION = "DELETE hl.* FROM hero_location hl "
                + "JOIN superHero sh ON hl.heroId = sh.heroId WHERE sh.powerId = ?";
        jdbc.update(DELETE_HERO_LOCATION, id);
        
        final String DELETE_HERO = "DELETE FROM superHero WHERE powerId = ?";
        jdbc.update(DELETE_HERO, id);
        
        final String DELETE_SUPERPOWER = "DELETE FROM superPower WHERE powerId = ?";
        jdbc.update(DELETE_SUPERPOWER, id);   
    }
}
