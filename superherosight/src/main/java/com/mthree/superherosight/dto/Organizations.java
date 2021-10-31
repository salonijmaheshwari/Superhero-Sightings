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
public class Organizations {
    private int orgId;
    private String orgName;
    private String orgDescription;
    private String orgContact;
    private String orgAddress;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getOrgContact() {
        return orgContact;
    }

    public void setOrgContact(String orgContact) {
        this.orgContact = orgContact;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.orgId;
        hash = 97 * hash + Objects.hashCode(this.orgName);
        hash = 97 * hash + Objects.hashCode(this.orgDescription);
        hash = 97 * hash + Objects.hashCode(this.orgContact);
        hash = 97 * hash + Objects.hashCode(this.orgAddress);
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
        final Organizations other = (Organizations) obj;
        if (this.orgId != other.orgId) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.orgDescription, other.orgDescription)) {
            return false;
        }
        if (!Objects.equals(this.orgContact, other.orgContact)) {
            return false;
        }
        if (!Objects.equals(this.orgAddress, other.orgAddress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organizations{" + "orgId=" + orgId + ", orgName=" + orgName + ", orgDescription=" + orgDescription + ", orgContact=" + orgContact + ", orgAddress=" + orgAddress + '}';
    }
    
    
}
