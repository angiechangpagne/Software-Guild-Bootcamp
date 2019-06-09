/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;

/**
 *
 * 
 */
public enum Denomination {
    PENNY         (new BigDecimal("0.01")), 
    NICKLE        (new BigDecimal("0.05")), 
    DIME          (new BigDecimal("0.10")), 
    QUARTER       (new BigDecimal("0.25")), 
    HALF_DOLLAR   (new BigDecimal("0.50")), 
    //DOLLAR_COIN(new BigDecimal("1.00")),
    DOLLAR        (new BigDecimal("1.00")),
    FIVE_DOLLAR   (new BigDecimal("5.00")),
    TEN_DOLLAR    (new BigDecimal("10.00")),
    TWENTY_DOLLAR (new BigDecimal("20.00")),
    FIFTY_DOLLAR  (new BigDecimal("50.00")),
    HUNED_DOLLAR(new BigDecimal("100.00"));
    
    private final BigDecimal val;

    private Denomination(BigDecimal val) {
        this.val = val;
    }

    public BigDecimal getVal() {
        return this.val;
    }
}
