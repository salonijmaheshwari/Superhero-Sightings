/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.superherosight.dao;

import com.mthree.superherosight.dto.Organizations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author salon
 */

@Repository
public class OrganizationDaoDb implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    public static final class OrganizationMapper implements RowMapper<Organizations> {

        @Override
        public Organizations mapRow(ResultSet rs, int index) throws SQLException {
            Organizations org = new Organizations();
            
            org.setOrgId(rs.getInt("orgId"));
            org.setOrgName(rs.getString("orgName"));
            org.setOrgDescription(rs.getString("orgDescription"));
            org.setOrgContact(rs.getString("orgContact"));
            org.setOrgAddress(rs.getString("orgAddress"));

            return org;
        }
    }
     
    @Override
    public Organizations getOrganizationById(int id) {
        try{
            final String SELECT_ORG_BY_ID = "SELECT * FROM organizations WHERE orgId = ?";
            return jdbc.queryForObject(SELECT_ORG_BY_ID,new OrganizationMapper() , id);
        }catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Organizations> getAllOrganizations() {
        final String SELECT_ALL_ORGS = "SELECT * FROM organizations";
        return jdbc.query(SELECT_ALL_ORGS, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organizations addOrganization(Organizations organization) {
        final String INSERT_ORG = "INSERT INTO organizations(orgName,orgDescription,orgcontact,orgAddress) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORG,
                organization.getOrgName(),
                organization.getOrgDescription(),
                organization.getOrgContact(),
                organization.getOrgAddress());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrgId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organizations organization) {
            final String UPDATE_ORG = "UPDATE organizations SET orgName = ?, orgDescription = ?, orgContact = ?, orgAddress = ? WHERE orgId = ?"; 
            jdbc.update(UPDATE_ORG,
                    organization.getOrgName(),
                    organization.getOrgDescription(),
                    organization.getOrgContact(),
                    organization.getOrgAddress(),
                    organization.getOrgId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM hero_organization WHERE orgId = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        
        final String DELETE_ORG = "DELETE FROM organizations WHERE orgId = ?";
        jdbc.update(DELETE_ORG, id);
    }  
}
