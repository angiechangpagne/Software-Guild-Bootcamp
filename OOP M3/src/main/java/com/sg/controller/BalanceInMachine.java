/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import java.math.BigDecimal;

/**
 *
 * @author angiechangpagne
 *
 * keeping track of money in vending machine
 */
public class BalanceInMachine {

    private BigDecimal currentBalance = new BigDecimal("0.00");

    public void add(BigDecimal amount) {
        currentBalance = currentBalance.add(amount);
    }
    
    public void remove(BigDecimal amount){
        currentBalance = currentBalance.subtract(amount);
    }
    
    public BigDecimal getBalance(){
        return this.currentBalance;
    }
}
