/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.SuperPower;
import java.util.List;

/**
 *
 * @author salon
 */
public interface SuperPowerDao {
    SuperPower getSuperPowerById(int id);
    List<SuperPower> getAllSuperPowers();
    SuperPower addSuperPower(SuperPower superPower);
    void updateSuperPower(SuperPower superPower);
    void deleteSuperPowerById(int id);
}
