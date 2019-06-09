package Daos;
import Daos.DirectorDao;
import dtos.Director;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DirectorDaoDB implements DirectorDao {

    final String DELETE_DIRECTOR = "DELETE FROM director WHERE id=?";
    final String INSERT_DIRECTOR = "INSERT INTO director(id,name)" + "values(?,?)";
    final String UPDATE_DIRECTOR = "UPDATE director SET name=?" + "WHERE id=?";
    final String SELECT_ALL_DIRECTORS = "SELECT * FROM director";
    final String SELECT_DIRECTOR_BY_ID = "SELECT * FROM director WHERE id=?";

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Director> ReadAll() {
        return jdbc.query(SELECT_ALL_DIRECTORS, new DirectorMapper());
    }

    @Override
    public Director ReadById(int id) {
        try {
            return jdbc.queryForObject(SELECT_DIRECTOR_BY_ID, new DirectorMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Director Create(Director dir) {
        jdbc.update(INSERT_DIRECTOR, dir.getName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        dir.setId(newId);
        return dir;
    }

    @Override
    public void Update(int id, Director dir) {
        jdbc.update(UPDATE_DIRECTOR, dir.getName(), dir.getId());
    }

    @Override
    @Transactional
    public void Delete(int id) {
        final String DELETE_DIRECTOR = "DELETE FROM director" + "WHERE directorId=?";
        jdbc.update(DELETE_DIRECTOR, id);
        jdbc.update(DELETE_DIRECTOR, id);
    }

//internal private class
    public static final class DirectorMapper implements RowMapper<Director> {

        @Override
        public Director mapRow(ResultSet rs, int index) throws SQLException {
            Director dir = new Director();
            dir.setId(rs.getInt("id"));
            dir.setName(rs.getString("name"));
            return dir;
        }

    }

}
