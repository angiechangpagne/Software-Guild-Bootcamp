/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.mycompany.dtos.Game;

/**
 *
 * @author angela997
 */
public class GameNotFoundException extends Exception{
    private Game g1;

    public GameNotFoundException(String message, Game g1) {
        super(message);
        this.g1 = g1;
    }

    public Game getGame() {
        return g1;
    }

    public void setGame(Game g1) {
        this.g1 = g1;
    }

}
