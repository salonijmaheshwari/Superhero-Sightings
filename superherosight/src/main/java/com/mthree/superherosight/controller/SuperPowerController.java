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
import com.mthree.superherosight.dto.SuperPower;
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
public class SuperPowerController {

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

    @GetMapping("superPowers")
    public String displaySuperPower(Model model) {
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superPowers", superPowers);
        return "superPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request) {
        String superPowerName = request.getParameter("superPowerName");

        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName(superPowerName);

        superPowerDao.addSuperPower(superPower);

        return "redirect:/superPowers";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superPowerDao.deleteSuperPowerById(id);

        return "redirect:/superPowers";
    }
    
    @GetMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superPower = superPowerDao.getSuperPowerById(id);
        
        model.addAttribute("superPower", superPower);
        return "editSuperPower";
    }
    
    @PostMapping("editSuperPower")
    public String performEditSuperPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superPower = superPowerDao.getSuperPowerById(id);
        
        superPower.setSuperPowerName(request.getParameter("superPowerName"));
       
        
        superPowerDao.updateSuperPower(superPower);
        
        return "redirect:/superPowers";
    }
}
