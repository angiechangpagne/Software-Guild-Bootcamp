/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import com.sg.model.Change;
import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class View {

    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayEntryBanner() {
        io.println("==============================");
        io.println("\tVENDING MACHINE");
        io.println("==============================");
    }

    public void displayOutOfStock(List<VendableItem> allItemsOutOfStock) {
        allItemsOutOfStock.forEach((item) -> {
            io.print("\t");
            printItem(item);
        });
    }

    public void displayInStock(List<VendableItem> allItemsInStock) {
        allItemsInStock.forEach((item) -> {
            // append number onto front this will be used later
            // for choosing what to buy
            io.print((allItemsInStock.indexOf(item) + 1) + "\t");
            printItem(item);
        });

        io.println("ENTER  " + (allItemsInStock.size() + 1) + "  TO EXIT");
    }

    public void displayOutOfService() {
        io.println("Out of service!");
    }

    private void printItem(VendableItem item) {
        io.print(item.getName() + " --- $" + item.getPrice() + " --- ");
        if (item.getCount() <= 0) {
            io.println("Out Of Stock");
        } else {
            io.println(item.getCount() + "");
        }
    }

    public BigDecimal askForMoney() {
        BigDecimal result = null;
        do {
            String input = io.readString("Enter Money: $");
            try {
                result = new BigDecimal(input);
            } catch (NumberFormatException e) {
                io.println("Not a valid amount of money");
            }
        } while (result == null);
        result = result.setScale(2, RoundingMode.DOWN);
        return result;
    }

    public void displayBalance(BigDecimal balance) {
        io.println("The machines display reads: $" + balance);
    }

    public int askForChoice(int min, int max) {
        int result = -1;
        do {
            result = io.readInt("Enter Choice: ", min, max);
            if (result == -1) {
                io.println("Not a valid choice");
            }
        } while (result == -1);
        return result;
    }

    public void displayVend(VendableItem item) {
        io.println("'Ka Clunk' 'The machine dispensed a " + item.getName() + "'");
    }

    public void displaChange(Change change) {
        io.println("'Coins rattle into the change return' Theres");
        if (change.getQuarters() > 0) {
            io.println(change.getQuarters() + " Quaters");
        }
        if (change.getDimes() > 0) {
            io.println(change.getDimes() + " Dimes");
        }
        if (change.getNickles() > 0) {
            io.println(change.getNickles() + " Nickles");
        }
        if (change.getPennies() > 0) {
            io.println(change.getPennies() + " Pennies");
        }
        io.println("in change");
    }

    public void displayInsufficentFundsMsg(BigDecimal amountInMachine, VendableItem item) {
        BigDecimal amountToAdd = item.getPrice().subtract(amountInMachine);
        io.println("Insufficent funds to purchase "
                + item.getName()
                + " Theres only $"
                + amountInMachine
                + " in the machine. Please add $"
                + amountToAdd
                + " to buy a " + item.getName());
    }

    public void displayExitMessage() {
        io.println("Thanks for vending! Goodbye!");
    }

    public void displayError(String message) {
        io.println(message);
    }

    public void displayKeepVending() {
        io.println("You added your change back into the machine ");
    }

    public boolean askToKeepVending() {
        String userInput = io.readString("Buy something else? (y/n) ");
        // check if last char is a y
        return "y".equalsIgnoreCase(userInput.charAt(userInput.length() - 1) + "");
    }
}
