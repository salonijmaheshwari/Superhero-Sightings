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
import com.mthree.superherosight.dto.SuperHeroSight;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author salon
 */

@Controller
public class LatestSightingsController {
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
    
    @GetMapping("homepage")
    public String displayLatestSightings(Model model) {
        List<SuperHeroSight> latestsights = superHeroSightDao.getLatestTenSightings();
        model.addAttribute("latestSights", latestsights);
        return "homepage";
        
    }    
}
