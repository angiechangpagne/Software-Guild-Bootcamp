/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.Dao;
import com.sg.dao.PersistanceException;
import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class ServiceFileImpl implements Service {

    private final Dao dao;

    public ServiceFileImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public VendableItem getItem(String name) throws PersistanceException, NoItemInventoryException {
        VendableItem item = dao.getItem(name);
        if (item != null && item.getCount() == 0) {
            throw new NoItemInventoryException();
        }
        return item;
    }

    @Override
    public void setCount(String name, int newCount) throws PersistanceException {
        if (newCount < 0) {
            throw new PersistanceException("newCount must be >= 0");
        }
        dao.setCount(name, newCount);
    }

    @Override
    public List<VendableItem> getAllItems() throws PersistanceException {
        return dao.getAllItems();
    }

    @Override
    public List<VendableItem> getAllItemsInStock() throws PersistanceException {
        return dao.getAllItems()
                .stream()
                .filter((item) -> item.getCount() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<VendableItem> getAllItemsOutOfStock() throws PersistanceException {
        return dao.getAllItems()
                .stream()
                .filter((item) -> item.getCount() <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal vendItem(BigDecimal amountInMachine, VendableItem item) throws InsufficentFundsException, PersistanceException {
        if (item.getPrice().compareTo(amountInMachine) > 0) {
            throw new InsufficentFundsException("Insufficent Funds");
        }
        setCount(item.getName(),item.getCount()-1);
        return amountInMachine.subtract(item.getPrice());
    }

    /* 
    * ==========================================================================
    * TO BE IMPLEMENTED AS PART OF ADMIN INTERFACE
    * ==========================================================================
     */
    @Override
    public VendableItem addItem(VendableItem item) throws PersistanceException {
        return dao.addItem(item);
    }

    @Override
    public VendableItem removeItem(String itemName) throws PersistanceException {
        return dao.removeItem(itemName);
    }

}
