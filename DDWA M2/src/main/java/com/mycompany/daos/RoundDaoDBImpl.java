package com.mycompany.daos;
import com.mycompany.dtos.Game;
import com.mycompany.dtos.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author angela997
 */
@Repository
public class RoundDaoDBImpl implements RoundDao {
    private final JdbcTemplate jdbcTemplate;
    //prepared statements
    private String deleteRound = "Delete from ROUND where roundId=?;";
    private String updateRound = "Update ROUND Set result=? Where gameId =?;";
    private String insertRound = "INSERT INTO ROUND(guess,result, gameId) VALUES (?,?,?);";
    //private String selectRounds="Select * from ROUND";
    private String selectRoundById = "Select roundId, guess,result,gameId From ROUND where roundId=?;";
    private String selectRoundsByGameId = "Select roundId, guess, gameId, result From ROUND gameId=?;";
    @Autowired
    public RoundDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    @Override
    public Round Create(Round entity) {
        this.jdbcTemplate.update(insertRound, entity.getGuess(), entity.getResult(),entity.getGameId());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        entity.setRoundId(newId);
        return entity;
    }
    @Transactional
    @Override
    public void Delete(int id) {
        this.jdbcTemplate.update(this.deleteRound, id);
    }

    @Override
    public List<Round> ReadAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public List<Round> ReadByGameId(int id) {
                return this.jdbcTemplate.query(selectRoundsByGameId, new RoundJDBCMapper(), id);
    }
    @Override
    public Round ReadById(int id) {
        return this.jdbcTemplate.queryForObject(selectRoundById, new RoundJDBCMapper(), id);
    }

    @Override
    public void Update(int gameId, Round entity) {
        this.jdbcTemplate.update(updateRound, entity.getResult(), gameId);
    }
    private class RoundJDBCMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round r1 = new Round();
            Game g1 = new Game();
            
            r1.setRoundId(rs.getInt("RoundId"));
            r1.setGuess(rs.getString("Guess"));
            r1.setResult(rs.getString("Id"));
            //r1.setReleaseDate(new java.sql.Date(rs.getDate("releaseDate").getTime()).toLocalDate());
            g1.setGameId(rs.getInt("GameId"));
            g1.setGameStatus(rs.getBoolean("GameStatus"));
            g1.setAnswer(rs.getString("GameAnswer"));
            //r1.setGame(g1);
            return r1;
        }
    }
}