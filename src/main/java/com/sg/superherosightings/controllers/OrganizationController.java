package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.OrganizationService;
import com.sg.superherosightings.services.SuperService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrganizationController {

    private OrganizationService orgService;

    private SuperService superService;

    @Autowired
    private LocationService locationService;

    @Autowired
    public OrganizationController(OrganizationService orgService, SuperService superService, LocationService locationService) {
        this.orgService=orgService;
        this.superService=superService;
        this.locationService=locationService;
    }

    //controller thymleaf html json response
    //POST readAll 
    @GetMapping("organizations")
    public String viewAll(Model model) {
        List<Organization> organizations = orgService.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }
    //readById

    
    @GetMapping("viewOrganization")
    public String view(HttpServletRequest request, Model model) {
        int orgId = Integer.parseInt(request.getParameter("organizationId"));
        List<Super>supers=superService.getSuperByOrganization(orgId);
        model.addAttribute("supers", supers);
        
        model.addAttribute("organization", orgService.getOrganizationById(orgId));
        return "viewOrganization";
    }

    /**
    @GetMapping("addOrganization")
    public String add(Model model) {
        Organization o = new Organization();
        model.addAttribute("organization", o);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("supers", superService.getAllSupers());
        return "addOrganization";
    }*/

    @PostMapping("addOrganization")
    public String add(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String locationId = request.getParameter("locationId");
        String[] supersId = request.getParameterValues("supersId");
        Organization o = new Organization();
        //TODO: Loop through supersId and get the super from the super service
        List<Super> supers = new ArrayList<>();
        for (String id : supersId) {
            Super s = superService.getSuperById(Integer.parseInt(id));

            supers.add(s);

        }
        o.setSupers(supers);
        o.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));

        o.setDescription(description);
        //TODO: Add Super to a list of Super List<Super> supers

        //TODO: Set  to o.setSupers(supers)
        //TODO: Retrieve location from location service
        //TODO: o.setLocation(location)
        o.setName(name);

        o.setDescription(description);

        orgService.addOrganization(o);

        return "redirect:/organizations";
    }

    //delete
    @GetMapping("deleteOrganization")
    public String delete(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        orgService.deleteOrganization(id);

        return "redirect:/organizations";

    }

    //edit,
    @GetMapping("editOrganization")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Location>locations=locationService.getAllLocations();
        model.addAttribute("locations",locations);
        Organization o = orgService.getOrganizationById(id);
        model.addAttribute("organization", o);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String edit(Organization o, BindingResult result,HttpServletRequest request,Model model) {

        int id = Integer.parseInt(request.getParameter("id"));
       

        o.setName(request.getParameter("name"));
        o.setDescription(request.getParameter("description"));
        orgService.updateOrganization(o);
        model.addAttribute("organization",o);

        return "redirect:/organizations";
    }
}
