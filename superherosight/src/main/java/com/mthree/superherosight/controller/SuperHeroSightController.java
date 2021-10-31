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
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperHeroSight;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author salon
 */
@Controller
public class SuperHeroSightController {

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
    
     

    @GetMapping("superHeroSightings")
    public String displaySightings(Model model) {
        List<SuperHeroSight> superHeroSights = superHeroSightDao.getAllSuperHeroSights();
        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
                
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("superHeroSights", superHeroSights);
        model.addAttribute("superHeros", superHeros);
        model.addAttribute("locations", locations);
        model.addAttribute("date1", new SuperHeroSight());
        return "superHeroSightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@ModelAttribute SuperHeroSight superHeroSight, HttpServletRequest request) {
        
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        superHeroSight.setSightDate(superHeroSight.getSightDate());
        superHeroSight.setSuperHero(superHeroDao.getSuperHeroById(Integer.parseInt(heroId)));
        superHeroSight.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        superHeroSightDao.addSuperHeroSight(superHeroSight);

        return "redirect:/superHeroSightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        superHeroSightDao.deleteSuperHeroSightById(id);
        return "redirect:/superHeroSightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        SuperHeroSight superHeroSight = superHeroSightDao.getSuperHeroSightById(id);
        List<SuperHero> superHeros = superHeroDao.getAllSuperHeros();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("superHeroSight", superHeroSight);
        model.addAttribute("superHeros", superHeros);
        model.addAttribute("locations", locations);
        model.addAttribute("date1", new SuperHeroSight());
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditCourse(@ModelAttribute SuperHeroSight superHeroSight, HttpServletRequest request) {

        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        superHeroSight.setSightDate(superHeroSight.getSightDate());
        superHeroSight.setSuperHero(superHeroDao.getSuperHeroById(Integer.parseInt(heroId)));
        superHeroSight.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        superHeroSightDao.updateSuperHeroSight(superHeroSight);

        return "redirect:/superHeroSightings";
    }
    
    @PostMapping("superHeroSightingsForADate")
    public String getSightingForADate(Model model,HttpServletRequest request) {
        
        String Date = request.getParameter("sightDate");
        Enumeration<String> names = request.getParameterNames();
       // LocalDate newDate = LocalDate.parse(Date);
        List<SuperHeroSight> sightsForADate = superHeroSightDao.getAllSuperHeroSightsForADate(LocalDate.parse(Date));
        model.addAttribute("sightsForADate", sightsForADate);
        return "superHeroSightingsForADate";
      
     
    }
    
   
     

}
