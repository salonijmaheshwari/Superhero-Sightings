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
public class SuperHeroSightDaoDbTest {
    
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
    
    public SuperHeroSightDaoDbTest() {
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
    public void testAddAndGetSuperHeroSight(){
        
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
        
        SuperHeroSight fromDao = superHeroSightDao.getSuperHeroSightById(superHeroSight.getSightId());
        assertEquals(superHeroSight, fromDao);
    }

    /**
     * Test of getSuperHeroSightById method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testGetSuperHeroSightById() {
    }

    /**
     * Test of getAllSuperHeroSights method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testGetAllSuperHeroSights() {
        
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
        
        SuperHeroSight superHeroSight2 = new SuperHeroSight();
        superHeroSight2.setSightDate(LocalDate.now());
        superHeroSight2.setSuperHero(superHero);
        superHeroSight2.setLocation(location);
        superHeroSight2= superHeroSightDao.addSuperHeroSight(superHeroSight2);
        
        List<SuperHeroSight> superHeroSights = superHeroSightDao.getAllSuperHeroSights();
        assertEquals(2,superHeroSights.size());
        assertTrue(superHeroSights.contains(superHeroSight));
        assertTrue(superHeroSights.contains(superHeroSight2));
    }

    /**
     * Test of addSuperHeroSight method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testAddSuperHeroSight() {
        
    }

    /**
     * Test of updateSuperHeroSight method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testUpdateSuperHeroSight() {
        
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
        
        SuperHeroSight fromDao = superHeroSightDao.getSuperHeroSightById(superHeroSight.getSightId());
        assertEquals(superHeroSight, fromDao);
        
        
        superHeroSight.setSightDate(LocalDate.parse("2018-12-30"));
        
        superHeroSightDao.updateSuperHeroSight(superHeroSight);
        
        assertNotEquals(superHeroSight, fromDao);
        
        fromDao = superHeroSightDao.getSuperHeroSightById(superHeroSight.getSightId());
        assertEquals(superHeroSight, fromDao);
        
    }

    /**
     * Test of deleteSuperHeroSightById method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testDeleteSuperHeroSightById() {
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
        
        SuperHeroSight fromDao = superHeroSightDao.getSuperHeroSightById(superHeroSight.getSightId());
        assertEquals(superHeroSight, fromDao);
        
        superHeroSightDao.deleteSuperHeroSightById(superHeroSight.getSightId());
        
        fromDao = superHeroSightDao.getSuperHeroSightById(superHeroSight.getSightId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllSuperHeroSightsForADate method, of class SuperHeroSightDaoDb.
     */
    @Test
    public void testGetAllSuperHeroSightsForADate() {
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
        
        
        Location location2 = new Location();
        location2.setLocationName("TajMahal");
        location2.setLocationDescription("Seven Wonders");
        location2.setLocationAddress("Agra");
        location2.setLatitude(new BigDecimal("84.486").setScale(8));
        location2.setLongitude(new BigDecimal("148.557465").setScale(8));
        location2 = locationDao.addLocation(location2);
        locations = new ArrayList<>();
        locations.add(location2);
        
        SuperHero superHero2 = new SuperHero();
        superHero2.setHeroName("IronMan");
        superHero2.setHeroDescription("Gets Power From his Suit");
        superHero2.setSuperPower(superPower);
        superHero2.setOrganizations(organizations);
        
        superHero2.setLocation(locations);
        superHero2 = superHeroDao.addSuperHero(superHero2);
        
        SuperHeroSight superHeroSight2 = new SuperHeroSight();
        superHeroSight2.setSightDate(LocalDate.now());
        superHeroSight2.setSuperHero(superHero2);
        superHeroSight2.setLocation(location2);
        superHeroSight2 = superHeroSightDao.addSuperHeroSight(superHeroSight2);
        
        
        SuperHeroSight superHeroSight3 = new SuperHeroSight();
        superHeroSight3.setSightDate(LocalDate.parse("2018-12-30"));
        superHeroSight3.setSuperHero(superHero);
        superHeroSight3.setLocation(location);
        superHeroSight3 = superHeroSightDao.addSuperHeroSight(superHeroSight3);
        
        List<SuperHeroSight> superHeroSights = superHeroSightDao.getAllSuperHeroSightsForADate(LocalDate.now());
        assertEquals(2,superHeroSights.size());
        assertTrue(superHeroSights.contains(superHeroSight));
        assertTrue(superHeroSights.contains(superHeroSight2));
        assertFalse(superHeroSights.contains(superHeroSight3));
           
    }    
}
