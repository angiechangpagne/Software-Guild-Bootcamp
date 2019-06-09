/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Change {

    private final int quarters;
    private final int dimes;
    private final int nickles;
    private final int pennies;
    
    /**
     * This method assumes total and itemPrice have a scale of 2
     * @param total the total amount
     * @param the item price
     * @throws IllegalArgumentException if itemPrice > total
     */
    public Change(BigDecimal total, BigDecimal itemPrice) {
        BigDecimal change = total.subtract(itemPrice);
        // this would be the result of passing in the wrong arguments into the function
        if(change.doubleValue() < 0){
            throw new IllegalArgumentException("Item Price > total");
        }
        // divide by 0.25 set int to quarters and change to the remainder (same for other steps)
        BigDecimal[] div = change.divideAndRemainder(Denomination.QUARTER.getVal());
        quarters = div[0].intValue();
        change = div[1];
        
        div = change.divideAndRemainder(Denomination.DIME.getVal());
        dimes = div[0].intValue();
        change = div[1];
        
        div = change.divideAndRemainder(Denomination.NICKLE.getVal());
        nickles = div[0].intValue();
        change = div[1];
        
        div = change.divideAndRemainder(Denomination.PENNY.getVal());
        pennies = div[0].intValue();
    }
    
    public Change(BigDecimal change){
        this(change,new BigDecimal("0.00"));
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickles() {
        return nickles;
    }

    public int getPennies() {
        return pennies;
    }

}
