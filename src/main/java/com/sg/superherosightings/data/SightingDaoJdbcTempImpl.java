/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.LocationDaoJdbcTempImpl.LocationMapper;
import com.sg.superherosightings.data.SuperDaoJdbcTempImpl.SuperMapper;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoJdbcTempImpl implements SightingDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (Date, LocationId) values (?, ?)";
    
    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingId = ?";
    
    private static final String SQL_DELETE_SUPERSIGHTING
            = "delete from SuperSighting where SightingId = ?";
    
    private static final String SQL_UPDATE_SIGHTING
            = "update sighting set date = ?, LocationId=? where SightingId= ?";
    
    private static final String SQL_SELECT_SIGHTING
            = "select * from Sighting where SightingId = ?";
    
    private static final String SQL_SELECT_SIGHTING_BY_LOCATIONID
            = "select * from Sighting where LocationId = ?";

    //readAll, get 
    private static final String SQL_SELECT_SIGHTING_DATES
            = "Select * from Sighting ORDER BY Date DESC Limit 10";
    
    private static final String SQL_SELECT_SUPERSIGHTING_SIGHTINGID_BY_SUPERID
            = "select SightingId from SightingSuper where SuperId = ?";
    
    private static final String SQL_SELECT_SUPERSIGHTING_SUPERID_BY_SIGHTINGID
            = "select SuperId from SuperSighting where SightingId = ?";

    //Find all the Sightings for a given super
    private static final String SQL_SELECT_SIGHTING_BY_SUPERID
            = "select s.SightingId, s.LocationId, s.Date from "
            + "Sighting s join SuperSighting ss on SuperId "
            + "where s.SightingId = ss.SightingId "
            + "and ss.SuperId = ?";

    //get many, get, read all bridge
    private static final String SQL_SELECT_SUPER_BY_SIGHTING_ID
            = "select s.SuperId, s.Name, s.Description from Super s join SightingSuper ss "
            + "on SightingId where s.SuperId = ss.SuperId "
            + "and ss.SightingId = ?";
    
    private static final String SQL_SELECT_LOCATION_BY_SIGHTINGID
            = "select l.* from Location l join "
            + "Sighting s on l.LocationId = s.LocationId where s.SightingId= ?";
    
    private static final String SQL_SELECT_ALL_SIGHTING
            = "select * from Sighting ORDER BY Date DESC";
    
    private static final String SQL_INSERT_SUPERSIGHTING
            = "insert into SightingSuper (SuperId, SightingId) values (?, ?)";
    
    @Override
    @Transactional
    public void addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getDate().toString(),
                sighting.getLocation().getLocationId());
        
        int sightingId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        sighting.setSightingId(sightingId);
    }
    
    @Override
    @Transactional
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE_SUPERSIGHTING, sightingId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }
    
    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getDate().toString(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
    }
    
    @Override
    @Transactional
    public Sighting getSightingById(int id) {
        try {
            Sighting s1 = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(), id);
            s1.setLocation(this.findLocationForSighting(s1));
            s1.setSupers(this.findSuperForSighting(s1));
            
            return s1;
            
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    @Transactional
    public List<Sighting> getSightingsDescDate() {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_DATES,
                new SightingMapper());
    }
    
    @Override
    @Transactional
    public void insertSuperSighting(Super superperson, Sighting sighting) {
        //inserts into the bridge
        jdbcTemplate.update(SQL_INSERT_SUPERSIGHTING,
                superperson.getSuperId(),
                sighting.getSightingId());
        
    }
    
    @Override
    @Transactional
    public List<Sighting> getSightingByLocationId(int locationId) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_LOCATIONID,
                new SightingMapper(),
                locationId);
        return associateLocationWithSighting(sightingList);
    }
    
    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> l1 = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTING,
                new SightingMapper());
        for (Sighting s : l1) {
            
            s.setLocation(findLocationForSighting(s));
            s.setSupers(findSuperForSighting(s));
           
        }
        
        return l1;
        
    }
    
    private List<Sighting> associateLocationWithSighting(List<Sighting> sightingList) {
        for (Sighting currentSighting : sightingList) {
            currentSighting.setLocation(findLocationForSighting(currentSighting));
        }
        return sightingList;
    }
    
    private Location findLocationForSighting(Sighting sighting) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTINGID,
                new LocationMapper(),
                sighting.getSightingId());
    }
    
    @Override
    @Transactional
    public List<Sighting> findSightingForSuper(Super superperson) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTING_BY_SUPERID,
                new SightingMapper(),
                superperson.getSuperId());
    }
    
    private List<Super> findSuperForSighting(Sighting s1) {
        return jdbcTemplate.query("select s.SuperId, s.Name, s.Description from Super s join SightingSuper ss "
                + "on SightingId where s.SuperId = ss.SuperId "
                + "and ss.SightingId = ?", new SuperMapper(), s1.getSightingId());
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {
        
        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setSightingId(rs.getInt("sightingId"));
            s.setDescription("description");
            s.setDate(LocalDate.parse(rs.getString("date")));
            return s;
        }
    }

    /**
     * private static final class LocationMapper implements RowMapper<Location>
     * {
     *
     * @Override public Location mapRow(ResultSet rs, int i) throws SQLException
     * { Location l = new Location(); l.setLocationId(rs.getInt("locationId"));
     * l.setName(rs.getString("name"));
     * l.setDescription(rs.getString("description"));
     * l.setAddress(rs.getString("address")); l.setCity(rs.getString("city"));
     * l.setState(rs.getString("state")); l.setZipcode(rs.getString("zipcode"));
     * l.setLatitude(rs.getBigDecimal("latitude"));
     * l.setLongitude(rs.getBigDecimal("longitude"));
     *
     * return l; } }
     */
}
