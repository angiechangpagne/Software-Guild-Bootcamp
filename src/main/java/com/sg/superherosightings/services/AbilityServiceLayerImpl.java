package com.sg.superherosightings.services;

import com.sg.superherosightings.data.AbilityDaoJdbcTempImpl;
import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Super;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilityServiceLayerImpl implements AbilityService{

    @Autowired private final AbilityDaoJdbcTempImpl abilityDao;
    
    public AbilityServiceLayerImpl(AbilityDaoJdbcTempImpl abilityDao){
        this.abilityDao = abilityDao;
    }
    
    
    @Override
    public void addAbility(Ability ability) {
        abilityDao.addAbility(ability);
    }

    
    @Override
    public void deleteAbility(int abilityId) {
        abilityDao.deleteAbility(abilityId);
    }

    
    @Override
    public void updateAbility(Ability ability) {
        abilityDao.updateAbility(ability);
    }


    @Override
    public Ability getAbilityById(int id) {
        return abilityDao.getAbilityById(id);
    }

  
    @Override
    public List<Ability> getAllAbilities() {
        return abilityDao.getAllAbilities();
    }

   
    @Override
    public List<Ability> findAbilityForSuper(Super superperson) {
        return abilityDao.findAbilityForSuper(superperson);
    }
    
}
