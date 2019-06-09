/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.daos;
import com.mycompany.dtos.*;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface RoundDao {
    Round Create(Round entity);
    void Delete(int id);
    List<Round> ReadAll();
    List<Round> ReadByGameId(int id); //all the rounds in a game
    Round ReadById(int id); //single round by roundID
    void Update(int id, Round entity);
}
