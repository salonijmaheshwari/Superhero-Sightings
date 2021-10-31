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
public class OrganizationDaoDbTest {
    
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
    
    public OrganizationDaoDbTest() {
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
    public void TestAddAndGetOrganization(){
        Organizations organization = new Organizations();
        organization.setOrgName("Marvel Entertainment");
        organization.setOrgDescription("America's leading entertainment organization.");
        organization.setOrgContact("555-555-5555");
        organization.setOrgAddress("NewYork");
        organization = organizationDao.addOrganization(organization);
        
        Organizations fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
    }

    /**
     * Test of getOrganizationById method, of class OrganizationDaoDb.
     */
    @Test
    public void testGetOrganizationById() {
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDb.
     */
    @Test
    public void testGetAllOrganizations() {
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
        
        List<Organizations> organizations = organizationDao.getAllOrganizations();
        
        assertEquals(2,organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    /**
     * Test of addOrganization method, of class OrganizationDaoDb.
     */
    @Test
    public void testAddOrganization() {
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDb.
     */
    @Test
    public void testUpdateOrganization() {
        Organizations organization = new Organizations();
        organization.setOrgName("Marvel Entertainment");
        organization.setOrgDescription("America's leading entertainment organization.");
        organization.setOrgContact("555-555-5555");
        organization.setOrgAddress("NewYork");
        organization = organizationDao.addOrganization(organization);
        
        Organizations fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
        
        organization.setOrgContact("777-777-7777");
        organizationDao.updateOrganization(organization);
        
        assertNotEquals(organization,fromDao);
        
        fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization,fromDao);      
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDb.
     */
    @Test
    public void testDeleteOrganizationById() {
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
        
        Organizations fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
        
        organizationDao.deleteOrganizationById(organization.getOrgId());
        
        fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertNull(fromDao);
    }
    
}
