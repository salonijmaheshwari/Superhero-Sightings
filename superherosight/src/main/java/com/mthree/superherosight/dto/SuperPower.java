/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dto;

import java.util.Objects;

/**
 *
 * @author salon
 */
public class SuperPower {
    private int powerId;
    private String superPowerName;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getSuperPowerName() {
        return superPowerName;
    }

    public void setSuperPowerName(String superPowerName) {
        this.superPowerName = superPowerName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.powerId;
        hash = 31 * hash + Objects.hashCode(this.superPowerName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuperPower other = (SuperPower) obj;
        if (this.powerId != other.powerId) {
            return false;
        }
        if (!Objects.equals(this.superPowerName, other.superPowerName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SuperPower{" + "powerId=" + powerId + ", superPowerName=" + superPowerName + '}';
    }
    
    
}
