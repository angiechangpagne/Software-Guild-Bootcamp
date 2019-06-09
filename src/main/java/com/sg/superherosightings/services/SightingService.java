/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface SightingService {

    void addSighting(Sighting sighting);

    void deleteSighting(int sightingId);

    List<Sighting> findSightingForSuper(Super superperson);

    List<Sighting> getAllSightings();

    List<Sighting> getRecentSightings(int id);

    Sighting getSightingById(int id);

    List<Sighting> getSightingByLocationId(int locationId);

    List<Sighting> getSightingsDescDate();

    void insertSuperSighting(Super superperson, Sighting sighting);

    void updateSighting(Sighting sighting);
    
}
