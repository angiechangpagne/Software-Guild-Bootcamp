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
public class VendableItem {
    private final String name;
    private final BigDecimal price;
    private int count;

    /** Creates a vendable item with a name, price, and a default count of 1**/
    public VendableItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.count = 1;
    }

    /** Creates a vendable item with a  name, price, and count**/
    public VendableItem(String name, BigDecimal price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    
}
