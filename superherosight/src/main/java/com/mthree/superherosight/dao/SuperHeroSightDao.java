/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.SuperHeroSight;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author salon
 */
public interface SuperHeroSightDao {
    SuperHeroSight getSuperHeroSightById(int id);
    List<SuperHeroSight> getAllSuperHeroSights();
    SuperHeroSight addSuperHeroSight(SuperHeroSight superHeroSight);
    void updateSuperHeroSight(SuperHeroSight superHeroSight);
    void deleteSuperHeroSightById(int id);
    
    List<SuperHeroSight> getAllSuperHeroSightsForADate(LocalDate date);
    public List<SuperHeroSight> getLatestTenSightings();
}
