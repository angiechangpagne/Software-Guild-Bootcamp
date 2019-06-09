/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface SuperDao {

    public void addSuper(Super superperson);

    public void deleteSuper(int superId);

    public void updateSuper(Super superperson);

    public Super getSuperById(int id);

    public List<Super> getAllSupers();
    
    public List<Super> getSuperBySighting(int sightingId);
    
    public List<Super> getSuperByAbility(int abilityId);
    
    public List<Super> getSuperByOrganization(int organizationId);
    
    public List<Super> findSuperForOrganization(Organization organization);
    
    public List<Super> findSuperForSighting(Sighting sighting);
    
    public void insertSuperAbility(Super superperson, Ability ability);
    
    public void insertSuperOrganization(Super superperson, Organization organization);
    
}
