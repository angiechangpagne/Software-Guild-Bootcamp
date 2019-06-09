/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.VendableItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class DaoFileImplTest {

    private final DaoFileImpl dao = new DaoFileImpl();

    VendableItem A = new VendableItem("A", new BigDecimal("1.25"), 0);
    VendableItem B = new VendableItem("B", new BigDecimal("1.50"), 0);
    VendableItem C = new VendableItem("C", new BigDecimal("2.00"), 0);
    VendableItem D = new VendableItem("D", new BigDecimal("5.00"), 0);

    @Before
    public void setUp() {
        try {
            // add item should return null the first time something is added
            dao.addItem(A);
            dao.addItem(B);
            dao.addItem(C);
            dao.addItem(D);
        } catch (PersistanceException e) {
            // swallow
        }
    }

    @After
    public void tearDown() {
        try {
            for (VendableItem item : dao.getAllItems()) {
                dao.removeItem(item.getName());
            }
        } catch (PersistanceException e) {
            // swallow
        }
    }

    /**
     * Test of getItem method, of class DaoFileImpl.
     */
    @Test
    public void testGetItem() {
        try {
            // names are from setup
            assertEquals(dao.getItem("A"), A);
            assertEquals(dao.getItem("B"), B);
            assertEquals(dao.getItem("C"), C);
            assertEquals(dao.getItem("D"), D);
            assertNull(dao.getItem("NullItem"));
        } catch (PersistanceException e) {
            fail();
        }
    }

    /**
     * Test of setCount method, of class DaoFileImpl.
     */
    @Test
    public void testSetCount() {
        try {
            dao.setCount("A", 10);
            assertEquals(dao.getItem("A").getCount(), 10);
        } catch (PersistanceException e) {
            fail();
        }
    }

    @Test
    public void testSetCountLessThanZero() {
        try {
            dao.setCount("A", -1);
            fail();
        } catch (PersistanceException e) {
            // should catch negative values as a error
        }
    }

    /**
     * Test of getAllItems method, of class DaoFileImpl.
     */
    @Test
    public void testGetAllItems() {
        try {
            List<VendableItem> items = dao.getAllItems();
            // size is specified in setup method
            assertEquals(items.size(), 4);
        } catch (PersistanceException e) {
            fail();
        }
    }
}
