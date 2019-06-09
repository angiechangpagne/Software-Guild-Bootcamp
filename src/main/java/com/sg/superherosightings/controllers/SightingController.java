package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.SightingService;
import com.sg.superherosightings.services.SuperService;
import java.time.LocalDate;
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
public class SightingController {

    private SightingService sightingService;
    private SuperService superService;
    private LocationService locationService;

    @Autowired
    public SightingController(SightingService sightingService, SuperService superService, LocationService locationService) {
        this.sightingService = sightingService;
        this.superService = superService;
        this.locationService = locationService;
    }

    @GetMapping("index")
    public String index(Model model) {
        List<Sighting> recentSightings = sightingService.getAllSightings();
        model.addAttribute("sightings", recentSightings);
        return "index";
    }

    @GetMapping("sightings")
    public String viewAll(Model model) {
        model.addAttribute("super", superService.getAllSupers());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("sightings", sightingService.getAllSightings());
        return "sightings";
    }

    @GetMapping("viewSighting")
    public String view(HttpServletRequest request, Model model) {
        int sightingId = Integer.parseInt(request.getParameter("sightingId"));
        model.addAttribute("sighting", sightingService.getSightingById(sightingId));
        return "viewSighting";
    }

    /**
     * @GetMapping("addSighting") public String add(Model model) {
     * model.addAttribute("sighting", new Sighting()); return "addSighting"; }
     */
    @PostMapping("addSighting")
    public String add(HttpServletRequest request) {
        String date = request.getParameter("date");

        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        Sighting sighting = new Sighting();

        sighting.setDate(LocalDate.parse(date));

        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        List<Super> supers = new ArrayList<>();
        for (String g : superIds) {
            supers.add(superService.getSuperById(Integer.parseInt(g)));
        }
        sighting.setSupers(supers);
        sightingService.addSighting(sighting);

        return "redirect:/sightings";
    }

    //Probably better to use a post method for delete
    @GetMapping("deleteSighting")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        sightingService.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        Sighting sighting = sightingService.getSightingById(id);
        List<Super> sups = superService.getAllSupers();
        List<Location> locs = locationService.getAllLocations();

        model.addAttribute("supers", sups);
        model.addAttribute("locs", locs);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String edit(Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String date=request.getParameter("date");
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        sighting.setDate(LocalDate.parse(date));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));

        List<Super> supers = new ArrayList<>();
        for (String g : superIds) {
            supers.add(superService.getSuperById(Integer.parseInt(g)));
        }
        sighting.setSupers(supers);
        sightingService.updateSighting(sighting);

        return "redirect:/sightings";
    }

}
