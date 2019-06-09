package Daos;

import Daos.DvdDao;
import dtos.Dvd;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DvdDaoDB implements DvdDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Dvd> ReadAll() {
        final String SELECT_ALL_ROOMS = "SELECT * FROM dvd";
        return jdbc.query(SELECT_ALL_ROOMS, new DvdMapper());

    }

    @Override
    public Dvd ReadById(int id) {
        try {
            final String SELECT_DVD_BY_ID = "SELECT * FROM dvd WHERE id=?";
            return jdbc.queryForObject(SELECT_DVD_BY_ID, new DvdMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Dvd> ReadByDirectorId(int directorId) {
        try {
            final String SELECT_DVD_BY_ID = "SELECT * FROM dvd WHERE directorId=?";
            return jdbc.query(SELECT_DVD_BY_ID, new DvdMapper(), directorId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Dvd Create(Dvd d1) {
        final String INSERT_DVD = "INSERT INTO dvd(id, d1) VALUES(?,?)";
        jdbc.update(INSERT_DVD, d1.getName(), d1);
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        d1.setId(newId);
        return d1;
    }

    @Override
    public void Update(int id, Dvd entity) {
        final String UPDATE_DVD = "UPDATE dvd SET name=?,rating=? where id=?";
        jdbc.update(UPDATE_DVD, entity.getName(), entity.getRating(), entity.getId());
    }

    @Override
    @Transactional
    public void Delete(int id) {
        final String DELETE_DVD_DIRECTOR_BY_DIRECTOR = "DELETE me.* FROM dvd_director me" + "JOIN dvd d1 ON me.dvdId=d1.id WHERE d1.dvdId=? ";
        jdbc.update(DELETE_DVD_DIRECTOR_BY_DIRECTOR, id);

        final String DELETE_DVD = "DELETE FROM room WHERE id=?";
        jdbc.update(DELETE_DVD, id);
    }

    private static final class DvdMapper implements RowMapper<Dvd> {

        @Override
        public Dvd mapRow(ResultSet rs, int index) throws SQLException {
            Dvd d1 = new Dvd();
            d1.setId(rs.getInt("id"));
            d1.setName(rs.getString("name"));
            d1.setDirectorId(rs.getInt("directorId"));
            d1.setRating(rs.getString("rating"));
            d1.setReleaseDate(LocalDate.parse(rs.getString("release date")));
            return d1;
        }

    }

}
