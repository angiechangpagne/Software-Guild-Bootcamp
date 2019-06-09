/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Ability;
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
public class AbilityDaoJdbcTempImpl implements AbilityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //create, post
    private static final String SQL_INSERT_ABILITY
            = "insert into Ability (Name,Description) values (?,?)";

    //delete, update, delete
    private static final String SQL_DELETE_ABILITY
            = "delete from Ability where AbilityId = ?";

    //update, put
    private static final String SQL_UPDATE_ABILITY
            = "update Ability set Description = ? where AbilityId = ?";

    //read, get
    private static final String SQL_SELECT_ABILITY
            = "select * from Ability where AbilityId = ?";

    //readAll, get
    //all the abilitys for a given super
    private static final String SQL_SELECT_ABILITY_BY_SUPERID
            = "select a.AbilityId, a.Description from Ability a "
            + "join SuperAbility sa on a.AbilityId = sa.AbilityId where "
            + "sa.SuperId = ?";

    //readAll, get
    private static final String SQL_SELECT_ALL_ABILITY
            = "select * from Ability";

    @Transactional
    public void addAbility(Ability ability) {
        jdbcTemplate.update(SQL_INSERT_ABILITY, ability.getName(),
                ability.getDescription());
        int abilityId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);

        ability.setAbilityId(abilityId);
    }

    @Transactional //since there is only one there not need be a rollback
    public void deleteAbility(int abilityId) {
        jdbcTemplate.update(SQL_DELETE_ABILITY, abilityId);
    }

    @Transactional
    public void updateAbility(Ability ability) {
        jdbcTemplate.update(SQL_UPDATE_ABILITY,
                ability.getDescription(),
                ability.getAbilityId());
    }

    @Transactional
    public Ability getAbilityById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ABILITY,
                    new AbilityMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Transactional
    //query gives all of them 
    public List<Ability> getAllAbilities() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ABILITY, new AbilityMapper());
    }

    @Transactional
    public List<Ability> findAbilityForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_ABILITY_BY_SUPERID,
                new AbilityMapper(),
                superperson.getSuperId());
    }

    public static final class AbilityMapper implements RowMapper<Ability> {

        public Ability mapRow(ResultSet rs, int i) throws SQLException {
            Ability a = new Ability();
            a.setAbilityId(rs.getInt("abilityId"));
            a.setDescription(rs.getString("description"));

            return a;
        }
    }
}
