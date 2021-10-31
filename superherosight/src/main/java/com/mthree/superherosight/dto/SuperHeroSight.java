/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dto;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author salon
 */
public class SuperHeroSight {
    private int sightId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sightDate;
    private SuperHero superHero;
    private Location location;

    public int getSightId() {
        return sightId;
    }

    public void setSightId(int sightId) {
        this.sightId = sightId;
    }

    public LocalDate getSightDate() {
        return sightDate;
    }

    public void setSightDate(LocalDate sightDate) {
        this.sightDate = sightDate;
    }

    public SuperHero getSuperHero() {
        return superHero;
    }

    public void setSuperHero(SuperHero superHero) {
        this.superHero = superHero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.sightId;
        hash = 79 * hash + Objects.hashCode(this.sightDate);
        hash = 79 * hash + Objects.hashCode(this.superHero);
        hash = 79 * hash + Objects.hashCode(this.location);
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
        final SuperHeroSight other = (SuperHeroSight) obj;
        if (this.sightId != other.sightId) {
            return false;
        }
        if (!Objects.equals(this.sightDate, other.sightDate)) {
            return false;
        }
        if (!Objects.equals(this.superHero, other.superHero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SuperHeroSight{" + "sightId=" + sightId + ", sightDate=" + sightDate + ", superHero=" + superHero + ", location=" + location + '}';
    }

    
}
