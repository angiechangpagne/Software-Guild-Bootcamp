/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.dao.PersistanceException;
import com.sg.model.Change;
import com.sg.model.VendableItem;
import com.sg.service.InsufficentFundsException;
import com.sg.service.Service;
import com.sg.view.View;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author angiechangpagne
 */
public class Controller {

    private final Service service;
    private final View view;

    private BalanceInMachine balance;

    public Controller(Service service, View view) {
        this.service = service;
        this.view = view;

        this.balance = new BalanceInMachine();
    }

    public void run() {
        view.displayEntryBanner();
        try {
            // start of program asks for money before getting choices
            showVendorItems();
            // ask for money
            addToMachine(askForMoney());
            view.displayBalance(getBalance());
            do {
                // user choice will always return a valid choice
                // view takes care of getting it
                int choice = getChoice();
                // choice they would choose to exit
                int exitChoice = service.getAllItemsInStock().size() + 1;
                // if they want to exit
                if (choice == exitChoice) {
                    break;
                }

                VendableItem item = choiceToItem(choice);
                try {
                    BigDecimal change = vendItem(item); // throws exception if cant pay
                    if (keepVending()) {
                        showItemsInStock();
                        view.displayKeepVending();
                        addToMachine(change);
                        view.displayBalance(getBalance());
                    } else {
                        // break if they dont want to vend
                        break;
                    }
                } catch (InsufficentFundsException ife) {
                    view.displayInsufficentFundsMsg(getBalance(), item);
                    // ask for more money
                    addToMachine(askForMoney());
                    view.displayBalance(getBalance());
                    // redisplay the in stock items
                    showItemsInStock();
                }
            } while (true);
        } catch (PersistanceException e) {
            view.displayError(e.getMessage());
            System.exit(0);
        }

        view.displayExitMessage();
    }

    /**
     * displays a list of out of stock items followed by a list of in stock
     * items
     *
     * if there are no items in stock a out of service message is displayed and
     * we exit
     */
    private void showVendorItems() throws PersistanceException {
        view.displayOutOfStock(service.getAllItemsOutOfStock());
        showItemsInStock();
    }

    private void showItemsInStock() throws PersistanceException {
        List<VendableItem> itemsInStock = service.getAllItemsInStock();
        if (itemsInStock.isEmpty()) {
            view.displayOutOfService();
            // nothing else to do so exit
            System.exit(0);
        } else {
            view.displayInStock(itemsInStock);
        }
    }

    private BigDecimal askForMoney() {
        return view.askForMoney();
    }

    private int getChoice() throws PersistanceException {
        return view.askForChoice(1, service.getAllItemsInStock().size() + 1);
    }

    /**
     * @return change after purchase *
     */
    private BigDecimal vendItem(VendableItem item) throws PersistanceException, InsufficentFundsException {
        BigDecimal change = service.vendItem(getBalance(), item);
        view.displayVend(item);
        // if change is not zero
        if (change.equals(BigDecimal.ZERO) == false) {
            view.displaChange(new Change(change));
        }
        // remove everything from machine
        removeFromMachine(getBalance());
        return change;
    }

    // helper, current choice is some item in the list of in stock items
    // its 1 indexed so we need to minus one and return the item
    private VendableItem choiceToItem(int currentChoice) throws PersistanceException {
        return service.getAllItemsInStock().get(currentChoice - 1);
    }

    private boolean keepVending() {
        return view.askToKeepVending();
    }

    // af few helpers I dont have to type as much
    private BigDecimal getBalance() {
        return this.balance.getBalance();
    }

    private void addToMachine(BigDecimal amount) {
        this.balance.add(amount);
    }

    private void removeFromMachine(BigDecimal amount) {
        this.balance.remove(amount);
    }
}
