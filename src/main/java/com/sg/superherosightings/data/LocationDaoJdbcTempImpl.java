/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author angela997
 */
@Repository
public class LocationDaoJdbcTempImpl implements LocationDao {

    @Autowired private final JdbcTemplate jdbcTemplate;
    
    public LocationDaoJdbcTempImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_LOCATION
            = "insert into Location (Name "
            + " Latitude, Longitude) values (?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationId = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set Name = ? "
            + "Longitude = ?, Latitude = ? where LocationId = ?";

    private static final String SQL_SELECT_LOCATION
            = "select * from Location where LocationId = ?";

    private static final String SQL_SELECT_LOCATION_BY_SIGHTINGID
            = "select l.LocationId, l.Name, l.Longitude, l.Latitude from Location l join "
            + "Sighting s on l.LocationId = s.LocationId where s.SightingId= ?";

    private static final String SQL_SELECT_ALL_LOCATION
            = "select * from Location";

       
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getName(),
                location.getLatitude(),
                location.getLongitude());

        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        location.setLocationId(locationId);
    }

    @Override
    @Transactional
    public void deleteLocation(int locationId) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getName(),
                location.getLongitude(),
                location.getLatitude(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public Location getLocationById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATION, new LocationMapper());
    }

    @Override
    @Transactional
    public Location getLocationIdBySighting(Sighting sighting) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTINGID,
                new LocationMapper(),
                sighting.getSightingId());
    }


    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();
            l.setLocationId(rs.getInt("locationId"));
            l.setName(rs.getString("name"));
            //l.setDescription(rs.getString("description"));
            l.setLatitude(rs.getBigDecimal("latitude"));
            l.setLongitude(rs.getBigDecimal("longitude"));

            return l;
        }
    }
}
