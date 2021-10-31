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
import java.math.BigDecimal;
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
public class LocationController {
    
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

    @GetMapping("locations")
    public String displayLocation(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String locationName = request.getParameter("locationName");
        String locationDescription = request.getParameter("locationDescription");
        String locationAddress = request.getParameter("locationAddress");
        BigDecimal latitude  = new BigDecimal(request.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(request.getParameter("longitude"));

        Location location = new Location();
        location.setLocationName(locationName);
        location.setLocationDescription(locationDescription);
        location.setLocationAddress(locationAddress);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setLocationAddress(request.getParameter("locationAddress"));
        location.setLatitude(new BigDecimal(request.getParameter("latitude")));
        location.setLongitude(new BigDecimal(request.getParameter("longitude")));
      
        locationDao.updateLocation(location);
        
        return "redirect:/locations";
    }
    
    @PostMapping("herolocation")
    public String getSuperHeroforlocation(Model model,HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
       
        List<SuperHero> superHeros = superHeroDao.getSuperHerosForLocation(location);
        model.addAttribute("location", location);
        model.addAttribute("superHeros", superHeros);      
        return "herolocation";
    }
    
}
