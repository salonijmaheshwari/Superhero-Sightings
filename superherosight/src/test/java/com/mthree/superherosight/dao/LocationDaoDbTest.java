/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.Location;
import com.mthree.superherosight.dto.Organizations;
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperHeroSight;
import com.mthree.superherosight.dto.SuperPower;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author salon
 */

@SpringBootTest
public class LocationDaoDbTest {
    
    @Autowired 
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SuperHeroDao superHeroDao;
    
    @Autowired
    SuperHeroSightDao superHeroSightDao;
    
    @Autowired
    SuperPowerDao superPowerDao;
    
    
    public LocationDaoDbTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocationById(location.getLocationId());
        }
        
        List<Organizations> organizations = organizationDao.getAllOrganizations();
        for(Organizations organization : organizations){
            organizationDao.deleteOrganizationById(organization.getOrgId());
        }
        
        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
        for(SuperHero superHero : superHeros){
            superHeroDao.deleteSuperHeroById(superHero.getHeroId());
        }
        
        List<SuperHeroSight> superHeroSights = superHeroSightDao.getAllSuperHeroSights();
        for(SuperHeroSight superHeroSight : superHeroSights){
            superHeroSightDao.deleteSuperHeroSightById(superHeroSight.getSightId());
        }
        
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        for(SuperPower superPower : superPowers){
            superPowerDao.deleteSuperPowerById(superPower.getPowerId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testAddAndGetLocation(){
        
        Location location = new Location();
        location.setLocationName("Statue of Unity");
        location.setLocationDescription("Statue of Sardar Patel.");
        location.setLocationAddress("Baroda");
        location.setLatitude(new BigDecimal("87.486").setScale(8));
        location.setLongitude(new BigDecimal("178.557465").setScale(8));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getLocationById method, of class LocationDaoDb.
     */
    @Test
    public void testGetLocationById() {
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDb.
     */
    @Test
    public void testGetAllLocations() {
        
        Location location = new Location();
        location.setLocationName("Statue of Unity");
        location.setLocationDescription("Statue of Sardar Patel.");
        location.setLocationAddress("Baroda");
        location.setLatitude(new BigDecimal("87.486").setScale(8));
        location.setLongitude(new BigDecimal("178.557465").setScale(8));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("TajMahal");
        location2.setLocationDescription("One of the seven Wonder.");
        location2.setLocationAddress("Agra");
        location2.setLatitude(new BigDecimal("78.486").setScale(8));
        location2.setLongitude(new BigDecimal("147.557465").setScale(8));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of addLocation method, of class LocationDaoDb.
     */
    @Test
    public void testAddLocation() {
        
    }

    /**
     * Test of updateLocation method, of class LocationDaoDb.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("Statue of Unity");
        location.setLocationDescription("Statue of Sardar Patel.");
        location.setLocationAddress("Baroda");
        location.setLatitude(new BigDecimal("87.486").setScale(8));
        location.setLongitude(new BigDecimal("178.557465").setScale(8));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        location.setLatitude(new BigDecimal("80.689").setScale(8));
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDb.
     */
    @Test
    public void testDeleteLocationById() {
        
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);
        
        Organizations organization = new Organizations();
        organization.setOrgName("Marvel Entertainment");
        organization.setOrgDescription("America's leading entertainment organization.");
        organization.setOrgContact("555-555-5555");
        organization.setOrgAddress("NewYork");
        organization = organizationDao.addOrganization(organization);
        List<Organizations> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Location location = new Location();
        location.setLocationName("Statue of Unity");
        location.setLocationDescription("Statue of Sardar Patel.");
        location.setLocationAddress("Baroda");
        location.setLatitude(new BigDecimal("87.486").setScale(8));
        location.setLongitude(new BigDecimal("178.557465").setScale(8));
        location = locationDao.addLocation(location);
        List<Location> locations = new ArrayList<>();
        locations.add(location);
        
        SuperHero superHero = new SuperHero();
        superHero.setHeroName("SuperMan");
        superHero.setHeroDescription("Most Powerful SuperHero");
        superHero.setSuperPower(superPower);
        superHero.setOrganizations(organizations);
        superHero.setLocation(locations);
        superHero = superHeroDao.addSuperHero(superHero);
        
        SuperHeroSight superHeroSight = new SuperHeroSight();
        superHeroSight.setSightDate(LocalDate.now());
        superHeroSight.setSuperHero(superHero);
        superHeroSight.setLocation(location);
        superHeroSight = superHeroSightDao.addSuperHeroSight(superHeroSight);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getLocationId());
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        assertNull(fromDao);
    }
    
}
