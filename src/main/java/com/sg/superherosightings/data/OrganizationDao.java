/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface OrganizationDao {

    public void addOrganization(Organization organization);

    public void deleteOrganization(int organizationId);

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(int id);

    public List<Organization> getAllOrganizations();

    public List<Organization> findOrganizationsForSuper(Super superperson);

}
