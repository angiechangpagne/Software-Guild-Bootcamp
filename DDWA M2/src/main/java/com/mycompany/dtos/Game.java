/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dtos;

import java.util.ArrayList;
/**
 *
 * @author angela997
 */
//every instance of a game will have a randomly generated permutation answer 
public class Game {
    int gameId;
    int games=0;
    boolean gameStatus; //game won
    String answer;
    ArrayList<Round> rounds;
    //int[]arr=[0,1,2,3,4,5,6,7,8,9];
    /////
    public Game() {
       // this.answer = Math.Random(9999);
        this.gameStatus=false;
        this.gameId=games++;
        this.rounds=new ArrayList<Round>();
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }//later on we will have radnomly generated answers from 10!-(10-4)/6! possibilities permutations

    public String getAnswer() {
        return answer;
    }
    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public boolean getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }
  
//game won   
}