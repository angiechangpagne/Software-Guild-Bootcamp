/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;


public class ChangeTest {

    @Test
    public void correctChangeA() {
        Change a = new Change(new BigDecimal("1.00"), new BigDecimal("0.50"));
        // 1.00-0.49==0.51;
        assertEquals(2,a.getQuarters());
        assertEquals(0,a.getDimes());
        assertEquals(0,a.getNickles());
        assertEquals(0,a.getPennies());
    }

    @Test
    public void correctChangeB() {
        Change b = new Change(new BigDecimal("1.00"), new BigDecimal("0.49"));
        // 1.00-0.49==0.51
        assertEquals(2,b.getQuarters()); 
        assertEquals(0,b.getDimes());
        assertEquals(0,b.getNickles());
        assertEquals(1,b.getPennies());
    }

    @Test
    public void correctChangeC() {
        Change c = new Change(new BigDecimal("1.00"), new BigDecimal("0.29"));
        // 1.00-0.29==0.71
        assertEquals(2,c.getQuarters());
        assertEquals(2,c.getDimes());
        assertEquals(0,c.getNickles());
        assertEquals(1,c.getPennies());
    }

    @Test
    public void correctChangeD() {
        Change d = new Change(new BigDecimal("12.00"), new BigDecimal("1.25"));
        // 12.00-1.25 == 10.75
        assertEquals(43,d.getQuarters());
        assertEquals(0,d.getDimes());
        assertEquals(0,d.getNickles());
        assertEquals(0,d.getPennies());
    }

    @Test
    public void correctChangeE() {
        Change e = new Change(new BigDecimal("1.00"), new BigDecimal("0.01"));
        // 1.00-0.01 0.99
        assertEquals(3,e.getQuarters()); // .99-75==24
        assertEquals(2,e.getDimes()); // 24-20=4
        assertEquals(0,e.getNickles());
        assertEquals(4,e.getPennies());
    }
    
    @Test
    public void correctChangeF() {
        Change e = new Change(new BigDecimal("0.10"), new BigDecimal("0.05"));
        // 0.10-0.05 = 0.05
        assertEquals(0,e.getQuarters());
        assertEquals(0,e.getDimes());
        assertEquals(1,e.getNickles());
        assertEquals(0,e.getPennies());
    }
    
    @Test
    public void correctChangeG() {
        Change e = new Change(new BigDecimal("0.10"), new BigDecimal("0.00"));
        // 0.10 -0.00 == 0.10
        assertEquals(0,e.getQuarters());
        assertEquals(1,e.getDimes());
        assertEquals(0,e.getNickles());
        assertEquals(0,e.getPennies());
    }
    
    @Test
    public void correctChangeH() {
        Change e = new Change(new BigDecimal("0.02"), new BigDecimal("0.01"));
        // 0.02 - 0.01 = 0.01
        assertEquals(0,e.getQuarters());
        assertEquals(0,e.getDimes());
        assertEquals(0,e.getNickles());
        assertEquals(1,e.getPennies());
    }
    
    @Test
    public void errorsWithWrongArgsTest(){
        try{
            Change change = new Change(new BigDecimal("0.01"),new BigDecimal("100.00"));
            fail();
        }catch(IllegalArgumentException e){
            // should throw this error
        }
    }
}
