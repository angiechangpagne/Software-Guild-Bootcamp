/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author angela997
 */
@Repository
public class OrganizationDaoJdbcTempImpl implements OrganizationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //ORGANIZATION PREPARED STATEMENTS
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (Name, Description)"
            + "values (?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationId = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set Name = ?, Description = ?"
            + "where "
            + "OrganizationId = ?";

    private static final String SQL_DELETE_ORGANIZATIONSUPER_FK
            = "delete from SuperOrganization where OrganizationId = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATION
            = "select o.`Name` as Organization_Name,o.`Description`,o.OrganizationId,l.* from `Organization` o join Location l on l.locationId=o.locationId ";
    private static final String SQL_SELECT_ORGANIZATION
            = SQL_SELECT_ALL_ORGANIZATION + " where OrganizationId = ?";

    //join is automatically inner join
    //all organizations for a super
    private static final String SQL_SELECT_ORGANIZATION_BY_SUPERID
            = SQL_SELECT_ALL_ORGANIZATION
            + " join "
            + "OrganizationSuper os on o.OrganizationId = os.OrganizationId where "
            + "os.SuperId = ?";

    @Override
    @Transactional
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription());

        int organizationId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);
        organization.setOrganizationId(organizationId);
    }

    @Override
    @Transactional
    public void deleteOrganization(int organizationId) {
        jdbcTemplate.update(SQL_DELETE_ORGANIZATIONSUPER_FK, organizationId);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getOrganizationId());
    }

    @Override
    @Transactional
    public Organization getOrganizationById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATION,
                new OrganizationMapper());
    }

    @Override
    @Transactional
    public List<Organization> findOrganizationsForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATION_BY_SUPERID,
                new OrganizationMapper(),
                superperson.getSuperId());
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization o = new Organization();
            o.setOrganizationId(rs.getInt("organizationId"));
            
            o.setName(rs.getString("organization_name"));
            
            Location location=new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));
          
            o.setLocation(location);
            
            o.setDescription(rs.getString("description"));
            // o.setType(rs.getString("type"));
            //o.setAddress(rs.getString("address"));
            // o.setCity(rs.getString("city"));
            //  o.setState(rs.getString("state"));
            // o.setZipcode(rs.getString("zipcode"));
            // o.setPhone(rs.getString("phone"));
            return o;
        }
    }

}
