/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.mycompany.dtos.Game;
import com.mycompany.dtos.Round;
import java.util.List;

/**
 *
 * @author angela997
 */
public interface GuessNumberService {
    
    List<Game>getAllGames();//list all
    Game getGameById(int gameId); //list singular
    Game beginGame(); //create
    String takeAGuess(int gameId, String guess);
    String compareGuess(int gameId, String guess, String answer);
    List<Round> getRounds(int gameId);
    
}
