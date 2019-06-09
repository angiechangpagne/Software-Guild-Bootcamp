package com.sg.superherosightings.services;

import com.sg.superherosightings.data.LocationDaoJdbcTempImpl;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceLayerImpl implements LocationService {

    @Autowired private LocationDaoJdbcTempImpl locationDao;

    public LocationServiceLayerImpl(LocationDaoJdbcTempImpl locationDao) {
        this.locationDao = locationDao;
    }

    
    @Override
    public void addLocation(Location location) {
        locationDao.addLocation(location);
    }

  
    @Override
    public void deleteLocation(int locationId) {
        locationDao.deleteLocation(locationId);
    }


    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }


    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location getLocationIdBySighting(Sighting sighting) {
        return locationDao.getLocationIdBySighting(sighting);
    }

}
