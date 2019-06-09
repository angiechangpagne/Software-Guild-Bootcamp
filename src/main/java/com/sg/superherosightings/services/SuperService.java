/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface SuperService {

    void addSuper(Super superperson);

    void deleteSuper(int superId);

    List<Super> findSuperForOrganization(Organization organization);

    List<Super> findSuperForSighting(Sighting sighting);

    List<Super> getAllSupers();

    List<Super> getSuperByAbility(int abilityId);

    Super getSuperById(int id);

    List<Super> getSuperByOrganization(int organizationId);

    List<Super> getSuperBySighting(int sightingId);

    void insertSuperAbility(Super superperson, Ability ability);

    void insertSuperOrganization(Super superperson, Organization organization);

    void updateSuper(Super superperson);
    
}
