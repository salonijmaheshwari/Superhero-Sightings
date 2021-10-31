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
public class OrganizationController {

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

    @GetMapping("organizations")
    public String displayOrganization(Model model) {
        List<Organizations> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String orgName = request.getParameter("orgName");
        String orgDescription = request.getParameter("orgDescription");
        String orgContact = request.getParameter("orgContact");
        String orgAddress = request.getParameter("orgAddress");

        Organizations organization = new Organizations();
        organization.setOrgName(orgName);
        organization.setOrgDescription(orgDescription);
        organization.setOrgContact(orgContact);
        organization.setOrgAddress(orgAddress);

        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organizationDao.deleteOrganizationById(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organizations organization = organizationDao.getOrganizationById(id);

        model.addAttribute("organizations", organization);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organizations organization = organizationDao.getOrganizationById(id);

        organization.setOrgName(request.getParameter("orgName"));
        organization.setOrgDescription(request.getParameter("orgDescription"));
        organization.setOrgContact(request.getParameter("orgContact"));
        organization.setOrgAddress(request.getParameter("orgAddress"));

        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }

    @PostMapping("heroorganization")
    public String getSuperHerofororganization(Model model,HttpServletRequest request) {
        String orgId = request.getParameter("orgId");
        
        Organizations organization = organizationDao.getOrganizationById(Integer.parseInt(orgId));
       
        List<SuperHero> superHeros = superHeroDao.getSuperHerosForOrganization(organization);
        model.addAttribute("organization", organization);
        model.addAttribute("superHeros", superHeros);      
        return "heroorganization";
    }

   

}
