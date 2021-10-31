/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.Organizations;
import java.util.List;

/**
 *
 * @author salon
 */
public interface OrganizationDao {
    Organizations getOrganizationById(int id);
    List<Organizations> getAllOrganizations();
    Organizations addOrganization(Organizations organization);
    void updateOrganization(Organizations organization);
    void deleteOrganizationById(int id);
}
