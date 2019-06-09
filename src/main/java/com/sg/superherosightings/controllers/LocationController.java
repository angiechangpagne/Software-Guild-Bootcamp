package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.LocationServiceLayerImpl;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    
    private LocationService locationService;
    
    @Autowired
    public LocationController(LocationService locationService){
        this.locationService=locationService;
    }

    @GetMapping("locations")
    public String viewAll(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    //pathvariable vs param
    @GetMapping("viewLocation")
    public String view(HttpServletRequest request, Model model) {
        int locId = Integer.parseInt(request.getParameter("locId"));
        model.addAttribute("location", locationService.getLocationById(locId));
        return "viewLocation";
    }

    /**
    @GetMapping("addLocation")
    public String add(Model model) {
        model.addAttribute("location", new Location());
        return "addLocation";
    }*/

    @PostMapping("addLocation")
    public String add(HttpServletRequest request) {
        //String[] sightingIds=request.getParameter("sightingIds");

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal latitude = new BigDecimal(request.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(request.getParameter("longitude"));

        Location loc = new Location();
        loc.setDescription(description);
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        loc.setName(name);
        locationService.addLocation(loc);
        return "redirect:/locations";
    }

    //Probably better to use a post method for delete
    @GetMapping("deleteLocation")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("locationId"));
        locationService.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("locationId"));
        Location loc = locationService.getLocationById(id);
        loc.setDescription(request.getParameter("description"));
        loc.setName(request.getParameter("name"));
        model.addAttribute("location", loc);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String edit(Location loc,BindingResult result,Model model, HttpServletRequest request) {
        //int id = Integer.parseInt(request.getParameter("id"));
        //Location loc = locationService.getLocationById(id);

        

        locationService.updateLocation(loc);

        return "redirect:/locations";
    }

}
