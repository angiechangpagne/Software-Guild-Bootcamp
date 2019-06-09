/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.AbilityDaoJdbcTempImpl.AbilityMapper;
import com.sg.superherosightings.data.OrganizationDaoJdbcTempImpl.OrganizationMapper;
import com.sg.superherosightings.data.SightingDaoJdbcTempImpl.SightingMapper;
import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Sighting;
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
public class SuperDaoJdbcTempImpl implements SuperDao {

    @Autowired private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//SUPER PREPARED STATEMENTS
    private static final String SQL_INSERT_SUPER
            = "insert into Super (Name, Description) values (?, ?)";

    private static final String SQL_INSERT_SUPERABILITY
            = "insert into AbilitySuper (SuperId, AbilityId) values (?, ?)";

    private static final String SQL_INSERT_SUPERSIGHTING
            = "insert into SightingSuper (SuperId, SightingId) values (?, ?)";

    private static final String SQL_INSERT_SUPERORGANIZATION
            = "insert into OrganizationSuper (SuperId, OrganizationId) values(?, ?)";

    //delete
    private static final String SQL_DELETE_SUPER
            = "delete from Super where SuperId= ?";

    private static final String SQL_DELETE_SUPERSIGHTING
            = "delete from SightingSuper where SuperId = ?";

    private static final String SQL_DELETE_SUPERABILITY
            = "delete from AbilitySuper where SuperId=?";

    private static final String SQL_DELETE_SUPERORGANIZATION
            = "delete from OrganizationSuper where SuperId = ?";

    private static final String SQL_DELETE_SUPERORGANIZATION_FK
            = "delete from OrganizationSuper where OrganizationId = ?";

    private static final String SQL_UPDATE_SUPER
            = "update Super set Name=?, Description=? where SuperId= ?";

    private static final String SQL_SELECT_SUPER
            = "select * from Super where SuperId= ?";

    //Find all the supers by a given sighting
    private static final String SQL_SELECT_SUPER_BY_SIGHTINGID
            = "select su.SuperId, su.Name, su.Description from Super su "
            + "join SightingSuper ss on su.SuperId = ss.SuperId where "
            + "ss.SightingId = ?";

    private static final String SQL_SELECT_SUPER_BY_SIGHTING_ID
            = "select s.SuperId, s.Name, s.Description from Super s join SightingSuper ss "
            + "on SightingId where s.SuperId = ss.SuperId "
            + "and ss.SightingId = ?";
    
    //all supers at an organization
    private static final String SQL_SELECT_SUPER_BY_ORGANIZATIONID
            = "select su.SuperId, su.Name, su.Description from Super su "
            + "join OrganizationSuper os on su.SuperId = os.SuperId where "
            + "os.OrganizationId = ?";
    
    //all supers with a given ability
    private static final String SQL_SELECT_SUPER_BY_ABILITYID
            = "select su.SuperId, su.Name, su.Description from Super su "
            + "join AbilitySuper sa on su.SuperId = sa.SuperId where "
            + "sa.AbilityId = ?";
    
    //readAll, get request analogous but in sql not http
    private static final String SQL_SELECT_ALL_SUPER
            = "select * from Super";

    private static final String SQL_SELECT_SIGHTING_BY_SUPERID
            = "select s.SightingId, s.LocationId, s.Date from "
            + "Sighting s join SightingSuper ss on SuperId "
            + "where s.SightingId = ss.SightingId "
            + "and ss.SuperId = ?";

    private static final String SQL_SELECT_ABILITY_BY_SUPERID
            = "select a.AbilityId, a.Description from Ability a "
            + "join AbilitySuper sa on a.AbilityId = sa.AbilityId where "
            + "sa.SuperId = ?";

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
    public void addSuper(Super superperson) {

        jdbcTemplate.update(SQL_INSERT_SUPER,
                superperson.getName(),
                superperson.getDescription());
        superperson.setSuperId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        insertSuperLists(superperson);
    }

    @Override
    @Transactional
    public void deleteSuper(int superId) {
        jdbcTemplate.update(SQL_DELETE_SUPERSIGHTING, superId);
        jdbcTemplate.update(SQL_DELETE_SUPERABILITY, superId);
        jdbcTemplate.update(SQL_DELETE_SUPERORGANIZATION, superId);
        jdbcTemplate.update(SQL_DELETE_SUPER, superId); //jappens last, rollback until this is done
    }

    @Override
    @Transactional
    public void updateSuper(Super superperson) {
        jdbcTemplate.update(SQL_UPDATE_SUPER,
                superperson.getName(),
                superperson.getDescription(),
                superperson.getSuperId());
        jdbcTemplate.update(SQL_DELETE_SUPERSIGHTING, superperson.getSuperId());
        jdbcTemplate.update(SQL_DELETE_SUPERABILITY, superperson.getSuperId());
        jdbcTemplate.update(SQL_DELETE_SUPERORGANIZATION, superperson.getSuperId());

        insertSuperLists(superperson);
    }

    @Override
    @Transactional
    public Super getSuperById(int id) {
        try {
            Super superperson = jdbcTemplate.queryForObject(SQL_SELECT_SUPER,
                    new SuperMapper(), id);
            if (superperson.getAbilities() != null) {
                superperson.setAbilities(findAbilityForSuper(superperson));
            }
            if (superperson.getSightings() != null) {
                superperson.setSightings(findSightingForSuper(superperson));
            }
            if (superperson.getOrganizations() != null) {
                superperson.setOrganizations(findOrganizationsForSuper(superperson));
            }
            return superperson;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    @Transactional
    public List<Super> getAllSupers() {
        List<Super> superList = jdbcTemplate.query(SQL_SELECT_ALL_SUPER,
                new SuperMapper());

        return associateTablesWithSupers(superList);
    }

    @Override
    @Transactional
    public List<Super> getSuperBySighting(int sightingId) {
        List<Super> superList
                = jdbcTemplate.query(SQL_SELECT_SUPER_BY_SIGHTINGID,
                        new SuperMapper(),
                        sightingId);

        return associateTablesWithSupers(superList);

    }

    @Override
    @Transactional
    public List<Super> getSuperByAbility(int abilityId) {
        List<Super> superList
                = jdbcTemplate.query(SQL_SELECT_SUPER_BY_ABILITYID,
                        new SuperMapper(),
                        abilityId);

        return associateTablesWithSupers(superList);
    }

    @Override
    @Transactional
    public List<Super> getSuperByOrganization(int organizationId) {
        List<Super> superList
                = jdbcTemplate.query(SQL_SELECT_SUPER_BY_ORGANIZATIONID,
                        new SuperMapper(),
                        organizationId);

        return associateTablesWithSupers(superList);
    }

    @Override
    @Transactional
    public List<Super> findSuperForSighting(Sighting sighting) {
        return jdbcTemplate.query(SQL_SELECT_SUPER_BY_SIGHTING_ID,
                new SuperMapper(),
                sighting.getSightingId());
    }

    @Override
    @Transactional
    public List<Super> findSuperForOrganization(Organization organization) {
        return jdbcTemplate.query(SQL_SELECT_SUPER_BY_ORGANIZATIONID,
                new SuperMapper(),
                organization.getOrganizationId());
    }

    @Override
    @Transactional
    public void insertSuperAbility(Super superperson, Ability ability) {
        jdbcTemplate.update(SQL_INSERT_SUPERABILITY,
                superperson.getSuperId(),
                ability.getAbilityId());
    }

    @Override
    @Transactional
    public void insertSuperOrganization(Super superperson, Organization organization) {
        jdbcTemplate.update(SQL_INSERT_SUPERORGANIZATION,
                superperson.getSuperId(),
                organization.getOrganizationId());
    }


    private List<Super> associateTablesWithSupers(List<Super> superList) {
        for (Super currentSuper : superList) {
            currentSuper.setAbilities(findAbilityForSuper(currentSuper));
            currentSuper.setSightings(findSightingForSuper(currentSuper));
            currentSuper.setOrganizations(findOrganizationsForSuper(currentSuper));
        }

        return superList;

    }

    @Transactional
    public List<Organization> findOrganizationsForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATION_BY_SUPERID,
                new OrganizationMapper(),
                superperson.getSuperId());
    }

    @Transactional
    public List<Ability> findAbilityForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_ABILITY_BY_SUPERID,
                new AbilityMapper(),
                superperson.getSuperId());
    }

    @Transactional
    public List<Sighting> findSightingForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_SUPERID,
                new SightingMapper(),
                superperson.getSuperId());
    }

    @Transactional
    public void insertSuperLists(Super superperson) {
        final int superId = superperson.getSuperId();
        final List<Ability> ability = superperson.getAbilities();
        final List<Sighting> sighting = superperson.getSightings();
        final List<Organization> organizations = superperson.getOrganizations();

        //insert into Supability table with an entry for each ability of this superhero.
        if (ability != null) {
            for (Ability currentAbility : ability) {
                jdbcTemplate.update(SQL_INSERT_SUPERABILITY,
                        superId,
                        currentAbility.getAbilityId());
            }
        }
        if (sighting != null) {
            for (Sighting currentSighting : sighting) {
                jdbcTemplate.update(SQL_INSERT_SUPERSIGHTING,
                        superId,
                        currentSighting.getSightingId());
            }
        }
        if (organizations != null) {
            for (Organization currentOrganization : organizations) {
                jdbcTemplate.update(SQL_INSERT_SUPERORGANIZATION,
                        superId,
                        currentOrganization.getOrganizationId());
            }
        }
    }

  
    public static final class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int i) throws SQLException {
            Super su = new Super();
            su.setSuperId(rs.getInt("superId"));
            su.setName(rs.getString("name"));
            su.setDescription(rs.getString("description"));
            return su;
        }
    }
/**
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setSightingId(rs.getInt("sightingId"));
            s.setDate(rs.getTimestamp("date"));
            return s;
        }
    }

    private static final class AbilityMapper implements RowMapper<Ability> {

        @Override
        public Ability mapRow(ResultSet rs, int i) throws SQLException {
            Ability p = new Ability();
            p.setAbilityId(rs.getInt("abilityId"));
            p.setDescription(rs.getString("description"));

            return p;
        }
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization or = new Organization();
            or.setOrganizationId(rs.getInt("organizationId"));
            or.setName(rs.getString("name"));
            or.setDescription(rs.getString("description"));
            or.setType(rs.getString("type"));
            or.setAddress(rs.getString("address"));
            or.setCity(rs.getString("city"));
            or.setState(rs.getString("state"));
            or.setZipcode(rs.getString("zipcode"));
            or.setPhone(rs.getString("phone"));
            return or;
        }
    }*/

}
