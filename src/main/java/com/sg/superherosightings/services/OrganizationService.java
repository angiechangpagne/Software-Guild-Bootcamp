/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface OrganizationService {

    void addOrganization(Organization organization);

    void deleteOrganization(int organizationId);

    List<Organization> findOrganizationsForSuper(Super superperson);

    List<Organization> getAllOrganizations();

    Organization getOrganizationById(int id);

    void updateOrganization(Organization organization);
    
}
