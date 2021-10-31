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
public class SuperPowerDaoDbTest {

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

    public SuperPowerDaoDbTest() {
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
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Organizations> organizations = organizationDao.getAllOrganizations();
        for (Organizations organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrgId());
        }

        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
        for (SuperHero superHero : superHeros) {
            superHeroDao.deleteSuperHeroById(superHero.getHeroId());
        }

        List<SuperHeroSight> superHeroSights = superHeroSightDao.getAllSuperHeroSights();
        for (SuperHeroSight superHeroSight : superHeroSights) {
            superHeroSightDao.deleteSuperHeroSightById(superHeroSight.getSightId());
        }

        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superPowerDao.deleteSuperPowerById(superPower.getPowerId());
        }

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSuperPower() {
        //arrange
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);

        //act
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getPowerId());

        //assert
        assertEquals(superPower, fromDao);
    }

    /**
     * Test of getSuperPowerById method, of class SuperPowerDaoDb.
     */
    @Test
    public void testGetSuperPowerById() {
    }

    /**
     * Test of getAllSuperPowers method, of class SuperPowerDaoDb.
     */
    @Test
    public void testGetAllSuperPowers() {
        //arrange
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);
        
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("Telepathy");
        superPower2 = superPowerDao.addSuperPower(superPower2);
        
        //act
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        
        //assert
        assertEquals(2,superPowers.size());
        assertTrue(superPowers.contains(superPower));
        assertTrue(superPowers.contains(superPower2));
    }

    /**
     * Test of addSuperPower method, of class SuperPowerDaoDb.
     */
    @Test
    public void testAddSuperPower() {
    }

    /**
     * Test of updateSuperPower method, of class SuperPowerDaoDb.
     */
    @Test
    public void testUpdateSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Immortal");
        superPower = superPowerDao.addSuperPower(superPower);
        
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getPowerId());
        assertEquals(superPower, fromDao);
        
        superPower.setSuperPowerName("Telepathy");
        superPowerDao.updateSuperPower(superPower);
        
        assertNotEquals(superPower,fromDao);
        
        fromDao=superPowerDao.getSuperPowerById(superPower.getPowerId());
        
        assertEquals(superPower,fromDao);
        
    }

    /**
     * Test of deleteSuperPowerById method, of class SuperPowerDaoDb.
     */
    @Test
    public void testDeleteSuperPowerById() {
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
        
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getPowerId());
        assertEquals(superPower, fromDao);
        
        superPowerDao.deleteSuperPowerById(superPower.getPowerId());
        
        fromDao = superPowerDao.getSuperPowerById(superPower.getPowerId());
        assertNull(fromDao);
            
    }

}
