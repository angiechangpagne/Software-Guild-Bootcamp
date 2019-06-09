/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface LocationService {

    void addLocation(Location location);

    void deleteLocation(int locationId);

    List<Location> getAllLocations();

    Location getLocationById(int id);

    Location getLocationIdBySighting(Sighting sighting);

    void updateLocation(Location location);
    
}
