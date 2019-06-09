package com.sg.superherosightings.services;

import com.sg.superherosightings.data.OrganizationDaoJdbcTempImpl;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceLayerImpl implements OrganizationService {

    @Autowired private final OrganizationDaoJdbcTempImpl orgDao;
    
    public OrganizationServiceLayerImpl(OrganizationDaoJdbcTempImpl orgDao){
        this.orgDao = orgDao;
    }

    @Override
    public void addOrganization(Organization organization) {
        orgDao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(int organizationId) {
        orgDao.deleteOrganization(organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {
        orgDao.updateOrganization(organization);
    }

  
    @Override
    public Organization getOrganizationById(int id) {
       return orgDao.getOrganizationById(id);
    }

 
    @Override
    public List<Organization> getAllOrganizations() {
        return orgDao.getAllOrganizations();
    }

    @Override
    public List<Organization> findOrganizationsForSuper(Super superperson) {
        return orgDao.findOrganizationsForSuper(superperson);
    }
    
}
