/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface LocationDao {

    public void addLocation(Location location);

    public void deleteLocation(int locationId);

    public void updateLocation(Location location);

    public Location getLocationById(int id);

    public List<Location> getAllLocations();

    public Location getLocationIdBySighting(Sighting sighting);
}

