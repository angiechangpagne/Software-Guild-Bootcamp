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
public class Round {
    int roundId;
    int gameId;
    String result; //result in the formate e:0:p:0
    String guess;
    public int getRoundId() {
        return roundId;
    }
//result in the formate e:0:p:0

    public String getResult() {
        return result;
    }
//result in the formate e:0:p:0

    public void setResult(String result) {
        this.result = result;
    }
//result in the formate e:0:p:0

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }
//result in the formate e:0:p:0

    public String getGuess() {
        return guess;
    }
//result in the formate e:0:p:0

    public void setGuess(String guess) {
        this.guess = guess;
    }
//result in the formate e:0:p:0

    public int getGameId() {
        return gameId;
    }
//result in the formate e:0:p:0

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
//result in the formate e:0:p:0
   
}
