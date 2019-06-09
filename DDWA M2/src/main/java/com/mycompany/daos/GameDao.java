/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.daos;

import java.util.List;
/**
 *
 * @author angela997
 */
import com.mycompany.dtos.*;
import java.util.List;

public interface GameDao {
Game Create(Game entity);
    void Delete(int id);
    List<Game> ReadAll();
    Game ReadById(int id);
    void Update(int id, Game entity);
}
