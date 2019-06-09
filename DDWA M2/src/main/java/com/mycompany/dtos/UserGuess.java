/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dtos;

/**
 *
 * @author angela997
 */
public class UserGuess {
    int gameId;
    String guess;

    public int getGameId() {
        return gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
    
}
