/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.PersistanceException;
import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.util.List;

public interface Service {

    /**
     * @param name the name of the item
     * @return a {@link VendableItem} with the given name or null no item with
     * the name exists*
     */
    public VendableItem getItem(String name) throws PersistanceException, NoItemInventoryException;

    /**
     *
     * @param name the name of the item
     * @param newCount the updated count for the item
     */
    public void setCount(String name, int newCount) throws PersistanceException;

    /**
     * @return all the items in the vending machine
     */
    public List<VendableItem> getAllItems() throws PersistanceException;

    /**
     * @return all the items in the vending machine that are in stock
     */
    public List<VendableItem> getAllItemsInStock() throws PersistanceException;

    /**
     * @return all the items in the vending machine that are in stock
     */
    public List<VendableItem> getAllItemsOutOfStock() throws PersistanceException;

    /**
     * Vends a item and removes one of the specific item from the machine
     * 
     * @param amountInMachine the amount currently in the vending machine
     * @param item the item to vend
     * @return the amount in the machine after vending the item
     * @throws InsufficentFundsException if item price > amountInMachine
     * @throws PersistanceException if there's a error updating the items count
     */
    public BigDecimal vendItem(BigDecimal amountInMachine, VendableItem item) throws InsufficentFundsException, PersistanceException;

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
    public VendableItem removeItem(String itemName) throws PersistanceException;
}
