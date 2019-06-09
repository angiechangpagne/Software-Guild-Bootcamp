/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface AbilityService {

    void addAbility(Ability ability);

    void deleteAbility(int abilityId);

    List<Ability> findAbilityForSuper(Super superperson);

    Ability getAbilityById(int id);

    List<Ability> getAllAbilities();

    void updateAbility(Ability ability);
    
}
