package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import com.sg.superherosightings.services.AbilityService;
import com.sg.superherosightings.services.OrganizationService;
import com.sg.superherosightings.services.SightingService;
import com.sg.superherosightings.services.SuperService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SuperController {

    private SuperService superService;

    private AbilityService abilityService;

    private SightingService sightingService;

    //private LocationService locationService;
    private OrganizationService orgService;

    @Autowired
    public SuperController(SuperService superService, SightingService sightingService, OrganizationService orgService, AbilityService abilityService) {
        this.abilityService = abilityService;
        this.orgService = orgService;
        this.sightingService = sightingService;
        this.superService = superService;
    }

//getAll
    @GetMapping("supers")
    public String viewAll(Model model) {
        model.addAttribute("supers", superService.getAllSupers());
        return "supers";
    }

    //get 1 by id
    @GetMapping("viewSuper")
    public String view(HttpServletRequest request, Model model) {
        int superId = Integer.parseInt(request.getParameter("superId"));
        model.addAttribute("super", superService.getSuperById(superId));
        return "viewSuper";
    }

    /**
     * @GetMapping("addSuper") public String add(Model model) { Super s = new
     * Super(); model.addAttribute("super", s);
     *
     * List<Ability> abilities = abilityService.getAllAbilities();
     * model.addAttribute("abilities", abilities);
     *
     * //add abilities loop and insert to return "addSuper"; }
     */
    @PostMapping("addSuper")
    public String add(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] abilityIds = request.getParameterValues("abilityId");
        String[] organizationIds = request.getParameterValues("organizationId");
        String[] sightingIds = request.getParameterValues("sightingId");
        Super sup = new Super();
        sup.setName(name);
        sup.setDescription(description);

        //we have a list of all the ability ids we want to find int abilities to list
        List<Ability> abilities = new ArrayList<>();
        for (String a : abilityIds) {
            abilities.add(abilityService.getAbilityById(Integer.parseInt(a)));
        }

        List<Organization> organizations = new ArrayList<>();
        for (String a : organizationIds) {
            organizations.add(orgService.getOrganizationById(Integer.parseInt(a)));
        }
        List<Sighting> sightings = new ArrayList<>();
        for (String a : sightingIds) {
            sightings.add(sightingService.getSightingById(Integer.parseInt(a)));
        }

        sup.setSightings(sightings);
        sup.setOrganizations(organizations);
        sup.setAbilities(abilities);
        //last thing for a deep copy
        superService.addSuper(sup);
        return "redirect:/supers";
    }

    //better to use a post method for delete
    @GetMapping("deleteSuper")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("superId"));
        superService.deleteSuper(id);
        return "redirect:/supers";
    }

    @GetMapping("editSuper")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("superId"));
        Super sup = superService.getSuperById(id);
        model.addAttribute("super", sup);

        List<Ability> abilities = abilityService.getAllAbilities();
        model.addAttribute("abilities", abilities);
        List<Organization> organizations = orgService.getAllOrganizations();
        model.addAttribute("organizations", organizations);

        return "editSuper";
    }

    @PostMapping("editSuper")
    public String edit(Super sup, BindingResult result, HttpServletRequest request) {
        String[] abilityIds = request.getParameterValues("abilityId");
        String[] organizationIds = request.getParameterValues("organizationId");

        List<Ability>abilities=new ArrayList<>();
        for(String g:abilityIds){
            abilities.add(abilityService.getAbilityById(Integer.parseInt(g)));
        }
        sup.setAbilities(abilities);
        
        List<Organization>organizations=new ArrayList<>();
        for(String g:organizationIds){
            organizations.add(orgService.getOrganizationById(Integer.parseInt(g)));
        }
        
        sup.setName(request.getParameter("name"));
        sup.setDescription(request.getParameter("description"));
        superService.updateSuper(sup);

        return "redirect:/supers";
    }

}
