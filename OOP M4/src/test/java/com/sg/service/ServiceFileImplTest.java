/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.DaoFileImpl;
import com.sg.dao.PersistanceException;
import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ServiceFileImplTest {

    private final ServiceFileImpl service = new ServiceFileImpl(new DaoFileImpl());

    VendableItem A = new VendableItem("A", new BigDecimal("1.25"), 1);
    VendableItem B = new VendableItem("B", new BigDecimal("1.50"), 1);
    VendableItem C = new VendableItem("C", new BigDecimal("2.00"), 1);
    VendableItem D = new VendableItem("D", new BigDecimal("5.00"), 1);

    @Before
    public void setUp() {
        try {
            // add item should return null the first time something is added
            service.addItem(A);
            service.addItem(B);
            service.addItem(C);
            service.addItem(D);
        } catch (PersistanceException e) {
            System.out.println("setup error " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            for (VendableItem item : service.getAllItems()) {
                service.removeItem(item.getName());
            }
        } catch (PersistanceException e) {
            System.out.println("teardown erro " + e.getMessage());
        }
    }

    /**
     * Test of getItem method, of class ServiceFileImpl.
     */
    @Test
    public void testGetItem() {
        try {
            // names are from setup
            assertEquals(service.getItem("A"), A);
            assertEquals(service.getItem("B"), B);
            assertEquals(service.getItem("C"), C);
            assertEquals(service.getItem("D"), D);
        } catch (PersistanceException | NoItemInventoryException e) {
            fail();
        }
    }

    @Test
    public void testGetItemNoExist() {
        try {
            // names are from setup
            assertNull(service.getItem("NotAName"));
        } catch (PersistanceException | NoItemInventoryException e) {
            fail();
        }
    }

    @Test
    public void testGettingCountZero() {
        VendableItem item = new VendableItem("X", new BigDecimal("1.00"), 0);
        try {
            service.addItem(item); // should not throw anything
            service.getItem("X"); // should throw NIIE
            fail();
        } catch (PersistanceException e) {
            fail();
        } catch (NoItemInventoryException i) {
            // pass item should throw
        }
    }

    /**
     * Test of setCount method, of class ServiceFileImpl.
     */
    @Test
    public void testSetCount() throws Exception {
        try {
            service.setCount("A", 10);
            assertEquals(service.getItem("A").getCount(), 10);
        } catch (PersistanceException e) {
            fail();
        }
    }

    @Test
    public void testSetCountLessThanZero() {
        try {
            service.setCount("A", -1);
            fail();
        } catch (PersistanceException e) {
            // should catch negative values as a error
        }
    }

    /**
     * Test of getAllItemsInStock method, of class ServiceFileImpl.
     */
    @Test
    public void testGetAllItemsInStock() throws Exception {
        try {
            for (VendableItem item : service.getAllItems()) {
                item.setCount(1);
            }
            // size is specified in setup method
            assertEquals(service.getAllItemsInStock().size(), service.getAllItems().size());
            for (VendableItem item : service.getAllItems()) {
                item.setCount(0);
            }
            assertEquals(service.getAllItemsInStock().size(), 0);
        } catch (PersistanceException e) {
            fail();
        }
    }

    /**
     * Test of getAllItemsOutOfStock method, of class ServiceFileImpl.
     */
    @Test
    public void testGetAllItemsOutOfStock() throws Exception {
        try {
            for (VendableItem item : service.getAllItems()) {
                item.setCount(1);
            }
            // size is specified in setup method
            assertEquals(service.getAllItemsOutOfStock().size(), 0);
            for (VendableItem item : service.getAllItems()) {
                item.setCount(0);
            }
            assertEquals(service.getAllItemsOutOfStock().size(), service.getAllItems().size());
        } catch (PersistanceException e) {
            fail();
        }
    }

    @Test
    public void testVendItem() {
        // A = 1.25
        // B = 1.50
        // C = 2.00
        // D = 5.00
        try {
            BigDecimal amount = new BigDecimal("9.75");

            BigDecimal change = service.vendItem(amount, service.getItem("A"));
            assertEquals(change, new BigDecimal("8.50"));
            amount = change;

            change = service.vendItem(amount, service.getItem("B"));
            assertEquals(change, new BigDecimal("7.00"));
            amount = change;

            change = service.vendItem(amount, service.getItem("C"));
            assertEquals(change, new BigDecimal("5.00"));
            amount = change;

            change = service.vendItem(amount, service.getItem("D"));
            assertEquals(change, new BigDecimal("0.00"));

        } catch (InsufficentFundsException | PersistanceException | NoItemInventoryException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void insufficentFundsToVend() {
        try {
            BigDecimal amount = new BigDecimal("1.00");
            VendableItem item = new VendableItem("Test", new BigDecimal("1.25"), 1);
            service.addItem(item); // add to vending machine
            service.vendItem(amount, item); // should only throw Insufficent funds exception
            fail();
        } catch (InsufficentFundsException e) {
            // should catch
        } catch (PersistanceException ex) {
            fail();
        }
    }
}
