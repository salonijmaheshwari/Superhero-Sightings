/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.controller;

import com.mthree.superherosight.dao.LocationDao;
import com.mthree.superherosight.dao.OrganizationDao;
import com.mthree.superherosight.dao.SuperHeroDao;
import com.mthree.superherosight.dao.SuperHeroDaoDb;
import com.mthree.superherosight.dao.SuperHeroSightDao;
import com.mthree.superherosight.dao.SuperPowerDao;
import com.mthree.superherosight.dto.Location;
import com.mthree.superherosight.dto.Organizations;
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperPower;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author salon
 */
@Controller
public class SuperHeroController {

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
    
    @Autowired
    SuperHeroDaoDb superHeroDaoDb;

    @GetMapping("superHeroes")
    public String displaySuperHeroes(Model model) {
        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        List<Organizations> organizations = organizationDao.getAllOrganizations();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("superHeros", superHeros);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("locations", locations);
        return "superHeroes";
    }

    @PostMapping("addSuperHero")
    public String addSuperHero(SuperHero superHero, HttpServletRequest request) {
        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");
        String[] locationIds = request.getParameterValues("locationId");

        superHero.setSuperPower(superPowerDao.getSuperPowerById(Integer.parseInt(powerId)));

        List<Organizations> organizations = new ArrayList<>();
        for (String orgId : orgIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
        }

        List<Location> locations = new ArrayList<>();
        for (String locationId : locationIds) {
            locations.add(locationDao.getLocationById(Integer.parseInt(locationId)));
        }

        superHero.setOrganizations(organizations);
        superHero.setLocation(locations);
        superHeroDao.addSuperHero(superHero);

        return "redirect:/superHeroes";
    }

    @GetMapping("superHeroDetail")
    public String superHeroDetail(Integer id, Model model) {
        SuperHero superHero = superHeroDao.getSuperHeroById(id);
        model.addAttribute("superHero", superHero);
        return "superHeroDetail";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(Integer id) {
        superHeroDao.deleteSuperHeroById(id);
        return "redirect:/superHeroes";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(Integer id, Model model) {
        SuperHero superHero = superHeroDao.getSuperHeroById(id);
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        List<Organizations> organizations = organizationDao.getAllOrganizations();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("superHero", superHero);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("locations", locations);
        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String performEditSuperHero(SuperHero superHero, HttpServletRequest request) {
        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");
        String[] locationIds = request.getParameterValues("locationId");

        superHero.setSuperPower(superPowerDao.getSuperPowerById(Integer.parseInt(powerId)));

        
        List<Organizations> organizations = new ArrayList<>();
        for (String orgId : orgIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
        }

        List<Location> locations = new ArrayList<>();
        for (String locationId : locationIds) {
            locations.add(locationDao.getLocationById(Integer.parseInt(locationId)));
        }

        superHero.setOrganizations(organizations);
        superHero.setLocation(locations);
        superHeroDao.updateSuperHero(superHero);

        return "redirect:/superHeroes";
    }
}
