/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.Location;
import com.mthree.superherosight.dto.Organizations;
import com.mthree.superherosight.dto.SuperHero;
import com.mthree.superherosight.dto.SuperPower;
import java.util.List;

/**
 *
 * @author salon
 */
public interface SuperHeroDao {
    SuperHero getSuperHeroById(int id);
    List<SuperHero> getAllSuperHeros();
    SuperHero addSuperHero(SuperHero superHero);
    void updateSuperHero(SuperHero superHero);
    void deleteSuperHeroById(int id);
    
    List<SuperHero> getSuperHerosForSuperPower(SuperPower superPower);
    List<SuperHero> getSuperHerosForOrganization(Organizations organization);
    List<SuperHero> getSuperHerosForLocation(Location location);
}