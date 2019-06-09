/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.services.AbilityService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author angela997
 */
@Controller
public class AbilityController {

    private AbilityService abilityService;

    @Autowired
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping("abilities")
    public String viewAll(Model model) {
        model.addAttribute("abilities", abilityService.getAllAbilities());
        return "abilities";
    }

    /**
     * @GetMapping("viewAbility") public String view(HttpServletRequest request,
     * Model model) { int ablityId =
     * Integer.parseInt(request.getParameter("abilityId"));
     * model.addAttribute("ability", abilityService.getAbilityById(ablityId));
     * return "viewAbility"; }
     */
    /**
     * @GetMapping("addAbility") public String add(Model model) {
     * model.addAttribute("ability", new Ability()); return "addAbility"; }
     */
    @PostMapping("addAbility")
    public String add(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Ability ab = new Ability();

        ab.setName(name);
        ab.setDescription(description);

        abilityService.addAbility(ab);

        return "redirect:/abilities";
    }

    //delete
    @GetMapping("deleteAbility")
    public String delete(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("abilityId"));
        abilityService.deleteAbility(id);
        return "redirect:/abilities";

    }

    //edit,
    @GetMapping("editAbility")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Ability ab = abilityService.getAbilityById(id);

        ab.setName(request.getParameter("name"));
        ab.setDescription(request.getParameter("description"));
        model.addAttribute("ability", ab);
        return "editAbility";
    }

    @PostMapping("editAbility")
    public String edit(Ability ab, BindingResult result) {

        //int id = Integer.parseInt(request.getParameter("id"));
        //Ability ab = abilityService.getAbilityById(id);
        abilityService.updateAbility(ab);

        return "redirect:/abilities";
    }

}
