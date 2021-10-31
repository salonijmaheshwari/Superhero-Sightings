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
public class SuperHeroDaoDbTest {
    
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
    
    public SuperHeroDaoDbTest() {
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
    
    
    public void testAddAndGetSperHero(){
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
        
        SuperHero fromDao = superHeroDao.getSuperHeroById(superHero.getHeroId());
        assertEquals(superHero,fromDao);
        
    }

    /**
     * Test of getSuperHeroById method, of class SuperHeroDaoDb.
     */
    @Test
    public void testGetSuperHeroById() {
    }

    /**
     * Test of getAllSuperHeros method, of class SuperHeroDaoDb.
     */
    @Test
    public void testGetAllSuperHeros() {
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
        
        SuperHero superHero2 = new SuperHero();
        superHero2.setHeroName("IronMan");
        superHero2.setHeroDescription("He gets power from his Suit");
        superHero2.setSuperPower(superPower);
        superHero2.setOrganizations(organizations);
        superHero2.setLocation(locations);
        superHero2 = superHeroDao.addSuperHero(superHero2);
        
        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
        assertEquals(2,superHeros.size());
        assertTrue(superHeros.contains(superHero));
        assertTrue(superHeros.contains(superHero2));
    }

    /**
     * Test of addSuperHero method, of class SuperHeroDaoDb.
     */
    @Test
    public void testAddSuperHero() {
    }

    /**
     * Test of updateSuperHero method, of class SuperHeroDaoDb.
     */
    @Test
    public void testUpdateSuperHero() {
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
        
        SuperHero fromDao = superHeroDao.getSuperHeroById(superHero.getHeroId());
        assertEquals(superHero,fromDao);
        
        superHero.setHeroName("IronMan");
        
        Organizations organization2 = new Organizations();
        organization2.setOrgName("DC Entertainment");
        organization2.setOrgDescription("America's Best Entertainment organization.");
        organization2.setOrgContact("555-777-5555");
        organization2.setOrgAddress("California");
        organization2 = organizationDao.addOrganization(organization2);
        organizations.add(organization2);
        superHero.setOrganizations(organizations);
        
        superHeroDao.updateSuperHero(superHero);
        
        assertNotEquals(superHero,fromDao);
        
        fromDao = superHeroDao.getSuperHeroById(superHero.getHeroId());
        assertEquals(superHero, fromDao); 
    }

    /**
     * Test of deleteSuperHeroById method, of class SuperHeroDaoDb.
     */
    @Test
    public void testDeleteSuperHeroById() {
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
        
        SuperHero fromDao = superHeroDao.getSuperHeroById(superHero.getHeroId());
        assertEquals(superHero,fromDao);
        
        superHeroDao.deleteSuperHeroById(superHero.getHeroId());
        
        fromDao = superHeroDao.getSuperHeroById(superHero.getHeroId());
        assertNull(fromDao);
    }

    /**
     * Test of getSuperHerosForSuperPower method, of class SuperHeroDaoDb.
     */
    @Test
    public void testGetSuperHerosForSuperPower() {
        
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);
        
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("Telepathy");
        superPower2 = superPowerDao.addSuperPower(superPower2);
        
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
        
        SuperHero superHero2 = new SuperHero();
        superHero2.setHeroName("SuperMan");
        superHero2.setHeroDescription("Most Powerful SuperHero");
        superHero2.setSuperPower(superPower2);
        superHero2.setOrganizations(organizations);
        superHero2.setLocation(locations);
        superHero2 = superHeroDao.addSuperHero(superHero2);
        
        SuperHero superHero3 = new SuperHero();
        superHero3.setHeroName("SuperMan");
        superHero3.setHeroDescription("Most Powerful SuperHero");
        superHero3.setSuperPower(superPower);
        superHero3.setOrganizations(organizations);
        superHero3.setLocation(locations);
        superHero3 = superHeroDao.addSuperHero(superHero3);
        
        List<SuperHero> superHeros = superHeroDao.getSuperHerosForSuperPower(superPower);
        assertEquals(2, superHeros.size());
        assertTrue(superHeros.contains(superHero));
        assertFalse(superHeros.contains(superHero2));
        assertTrue(superHeros.contains(superHero3));
        
        
    }

    /**
     * Test of getSuperHerosForOrganization method, of class SuperHeroDaoDb.
     */
    @Test
    public void testGetSuperHerosForOrganization() {
        
         
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);
        
        Organizations organization = new Organizations();
        organization.setOrgName("Marvel Entertainment");
        organization.setOrgDescription("America's leading entertainment organization.");
        organization.setOrgContact("555-555-5555");
        organization.setOrgAddress("NewYork");
        organization = organizationDao.addOrganization(organization);
        
        Organizations organization2 = new Organizations();
        organization2.setOrgName("DC Entertainment");
        organization2.setOrgDescription("One of the best Organization.");
        organization2.setOrgContact("555-777-5555");
        organization2.setOrgAddress("California");
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organizations> organizations = new ArrayList<>();
        organizations.add(organization);
        organizations.add(organization2);
        
        List<Organizations> organizations2 = new ArrayList<>();
        organizations2.add(organization2);
        
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
        
        SuperHero superHero2 = new SuperHero();
        superHero2.setHeroName("SuperMan");
        superHero2.setHeroDescription("Most Powerful SuperHero");
        superHero2.setSuperPower(superPower);
        superHero2.setOrganizations(organizations2);
        superHero2.setLocation(locations);
        superHero2 = superHeroDao.addSuperHero(superHero2);
        
        SuperHero superHero3 = new SuperHero();
        superHero3.setHeroName("SuperMan");
        superHero3.setHeroDescription("Most Powerful SuperHero");
        superHero3.setSuperPower(superPower);
        superHero3.setOrganizations(organizations);
        superHero3.setLocation(locations);
        superHero3 = superHeroDao.addSuperHero(superHero3);
        
        List<SuperHero> superHeros = superHeroDao.getSuperHerosForOrganization(organization);
        assertEquals(2, superHeros.size());
        assertTrue(superHeros.contains(superHero));
        assertFalse(superHeros.contains(superHero2));
        assertTrue(superHeros.contains(superHero3));
            
    }

    /**
     * Test of getSuperHerosForLocation method, of class SuperHeroDaoDb.
     */
    @Test
    public void testGetSuperHerosForLocation() {
        
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
        
        Location location2 = new Location();
        location2.setLocationName("TajMahal");
        location2.setLocationDescription("One of the seven Wonder.");
        location2.setLocationAddress("Agra");
        location2.setLatitude(new BigDecimal("78.486").setScale(8));
        location2.setLongitude(new BigDecimal("147.557465").setScale(8));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = new ArrayList<>();
        locations.add(location);
        locations.add(location2);
        
        List<Location> locations2 = new ArrayList<>();
        locations2.add(location2);
        
        SuperHero superHero = new SuperHero();
        superHero.setHeroName("SuperMan");
        superHero.setHeroDescription("Most Powerful SuperHero");
        superHero.setSuperPower(superPower);
        superHero.setOrganizations(organizations);
        superHero.setLocation(locations);
        superHero = superHeroDao.addSuperHero(superHero);
        
        SuperHero superHero2 = new SuperHero();
        superHero2.setHeroName("SuperMan");
        superHero2.setHeroDescription("Most Powerful SuperHero");
        superHero2.setSuperPower(superPower);
        superHero2.setOrganizations(organizations);
        superHero2.setLocation(locations2);
        superHero2 = superHeroDao.addSuperHero(superHero2);
        
        SuperHero superHero3 = new SuperHero();
        superHero3.setHeroName("SuperMan");
        superHero3.setHeroDescription("Most Powerful SuperHero");
        superHero3.setSuperPower(superPower);
        superHero3.setOrganizations(organizations);
        superHero3.setLocation(locations);
        superHero3 = superHeroDao.addSuperHero(superHero3);
        
        List<SuperHero> superHeros = superHeroDao.getSuperHerosForLocation(location);
        assertEquals(2, superHeros.size());
        assertTrue(superHeros.contains(superHero));
        assertFalse(superHeros.contains(superHero2));
        assertTrue(superHeros.contains(superHero3));
        
    }
    
}
