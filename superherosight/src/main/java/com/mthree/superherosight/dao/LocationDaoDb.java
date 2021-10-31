/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.Location;
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
public class LocationDaoDb implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setLocationAddress(rs.getString("locationAddress"));
            location.setLatitude(rs.getBigDecimal("latitude").setScale(8));
            location.setLongitude(rs.getBigDecimal("longitude").setScale(8));
            return location;
        }
    }

    @Override
    public Location getLocationById(int id) {
        try{
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(locationName, locationDescription, locationAddress, latitude, longitude) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLocationAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET locationName = ?, locationDescription = ?, locationAddress = ?, latitude = ?, longitude = ? WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLocationAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_HEROSIGHT="DELETE FROM herosight WHERE locationId= ?";
        jdbc.update(DELETE_HEROSIGHT, id);
        
        final String DELETE_HERO_LOCATION = "DELETE FROM hero_location WHERE locationId = ?";
        jdbc.update(DELETE_HERO_LOCATION, id);
        
        final String DELETE_Location = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(DELETE_Location, id);    
    }
    
}
