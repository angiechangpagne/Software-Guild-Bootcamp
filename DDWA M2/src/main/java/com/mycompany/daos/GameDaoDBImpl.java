/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.daos;

import com.mycompany.dtos.Game;
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
public class GameDaoDBImpl implements GameDao {

    private final JdbcTemplate jdbcTemplate;
    //prepared statements
    private String deleteGame = "Delete from GAME where gameId=?";
    private String updateGame = "Update GAME Set gameStatus = ? where gameId=?";
    private String insertGame = "INSERT INTO GAME(gameStatus, answer ) VALUES (?,?)";
    private String selectGameByID = "Select gameId,answer,gameStatus from GAME where gameId=?;";
    private String selectAll = "Select * from GAME;";

    @Autowired private JdbcTemplate jdbc;
    public GameDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Game Create(Game entity) {
        this.jdbcTemplate.update(insertGame,  entity.getGameStatus(), entity.getAnswer());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        entity.setGameId(newId);
        return entity;
    }

    
    //when you delete items, if you do not do a cascaded delete, null items become orphan references which will be garbage collected
    @Transactional
    @Override
    public void Delete(int id) {
        this.jdbcTemplate.update(this.deleteGame, id);
    }

    
    @Transactional
    @Override
    public List<Game> ReadAll() {
        return this.jdbcTemplate.query(selectAll, new GameJDBCMapper());
    }

    @Transactional
    @Override
    public Game ReadById(int id) {
        try {
            return this.jdbcTemplate.queryForObject(selectGameByID, new GameJDBCMapper(), id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return null;
        }
    }

    @Transactional
    @Override
    public void Update(int id, Game entity) {
        this.jdbcTemplate.update(updateGame, entity.getGameStatus(), entity.getGameId());
    }

    
    //getting a result set in jdbc accesses jcbc template from the database, which intializes the plain old java object(pojo)
    private class GameJDBCMapper implements RowMapper<Game> {

        //always use @Transactional to promote a rollback function
        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game g1 = new Game();

            g1.setGameId(rs.getInt("gameId"));
            g1.setAnswer(rs.getString("answer"));
            g1.setGameStatus(rs.getBoolean("gameStatus"));

            //rounds mapper 
            return g1;
        }

    }

}

