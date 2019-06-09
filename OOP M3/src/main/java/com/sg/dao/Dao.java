/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.VendableItem;
import java.util.List;

/**
 *
 * @aangiechangpagne
 */
public interface Dao {

    /**
     * @param name the name of the item
     * @return a {@link VendableItem} with the given name or null no item with
     * the name exists*
     */
    public VendableItem getItem(String name) throws PersistanceException;

    /**
     *
     * @param name the name of the item
     * @param newCount the updated count for the item
     */
    public void setCount(String name, int newCount) throws PersistanceException;

    /**
     * @return all the items in the vending machine *
     */
    public List<VendableItem> getAllItems() throws PersistanceException;
    
    /**
     * adds a new item to the vending machine
     *
     * @param item the item to add
     * @return null if its the first time a item was added, otherwise returns
     * the item
     */
    public VendableItem addItem(VendableItem item) throws PersistanceException;

    /**
     * removes a item from the vending machine
     *
     * @param name the name of the item to remove
     * @return the VendableItem removed or null if nothing was removed
     */
    public VendableItem removeItem(String name) throws PersistanceException;
}
