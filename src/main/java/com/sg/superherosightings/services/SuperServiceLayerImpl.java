/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.data.SuperDaoJdbcTempImpl;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author angela997
 */
@Service
public class SuperServiceLayerImpl implements SuperService {

    @Autowired private final SuperDaoJdbcTempImpl superDao;
    
    public SuperServiceLayerImpl(SuperDaoJdbcTempImpl superDao){
        this.superDao = superDao;
    }
    

    @Override
    public void addSuper(Super superperson) {
        superDao.addSuper(superperson);
    }


    @Override
    public void deleteSuper(int superId) {
        superDao.deleteSuper(superId);
    }


    @Override
    public void updateSuper(Super superperson) {
        superDao.updateSuper(superperson);
    }


    @Override
    public Super getSuperById(int id) {
        return superDao.getSuperById(id);
    }


    @Override
    public List<Super> getAllSupers() {
        return superDao.getAllSupers();
    }

    @Override
    public List<Super> getSuperBySighting(int sightingId) {
        return superDao.getSuperBySighting(sightingId);
    }

    
    @Override
    public List<Super> getSuperByAbility(int abilityId) {
        return superDao.getSuperByAbility(abilityId);
    }

 
    @Override
    public List<Super> getSuperByOrganization(int organizationId) {
        return superDao.getSuperByOrganization(organizationId);
    }


    @Override
    public List<Super> findSuperForOrganization(Organization organization) {
        return superDao.findSuperForOrganization(organization);
    }

  
    @Override
    public List<Super> findSuperForSighting(Sighting sighting) {
        return superDao.findSuperForSighting(sighting);
    }

    @Override
    public void insertSuperAbility(Super superperson, Ability ability) {
        superDao.insertSuperAbility(superperson, ability);
    }

    @Override
    public void insertSuperOrganization(Super superperson, Organization organization) {
        superDao.insertSuperOrganization(superperson, organization);
    }
    
}
