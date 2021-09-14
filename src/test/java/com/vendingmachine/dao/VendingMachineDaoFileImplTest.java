package com.vendingmachine.dao;

import com.vendingmachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeEach
    void setUp() throws Exception{
        String testFile = "testinventory.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testAddGet() throws Exception {
       // Create item
       String code = "A1";
       Item item = new Item(code);
       item.setName("Kit-Kat");
       item.setCost("0.55");
       item.setNumber("10");

       //  Add the item to the DAO, then retrieve it
       testDao.addItem(code, item);
       Item retrievedItem = testDao.getItem(code);

       // Check the data is equal
       assertEquals(item.getCode(), retrievedItem.getCode(), "Checking item code.");
       assertEquals(item.getName(), retrievedItem.getName(), "Checking item name.");
       assertEquals(item.getCost(), retrievedItem.getCost(), "Checking item cost.");
       assertEquals(item.getNumber(), retrievedItem.getNumber(), "Checking item number.");
    }

    @Test
    public void testAddGetAll() throws Exception {
        // Add items
        String code = "A1";
        Item item1 = new Item(code);
        item1.setName("Kit-Kat");
        item1.setCost("0.55");
        item1.setNumber("10");

        code = "B1";
        Item item2 = new Item(code);
        item2.setName("Snickers");
        item2.setCost("0.6");
        item2.setNumber("5");

        //  Add items to the DAO
        testDao.addItem(item1.getCode(), item1);
        testDao.addItem(item2.getCode(), item2);

        // Retrieve the list of all items within the DAO
        List<Item> allItems = testDao.getAllItems();

        // First check the general contents of the list
        assertNotNull(allItems, "The list of items must not null");
        assertEquals(2, allItems.size(),"List of items should have 2 students.");

        // Then the specifics
        assertTrue(testDao.getAllItems().contains(item1), "The list of students should include Kit-Kat.");
        assertTrue(testDao.getAllItems().contains(item2), "The list of students should include Snickers.");
    }

    @Test
    public void testRemove() throws Exception {
        // Add items
        String code = "A1";
        Item item1 = new Item(code);
        item1.setName("Kit-Kat");
        item1.setCost("0.55");
        item1.setNumber("10");

        code = "B1";
        Item item2 = new Item(code);
        item2.setName("Snickers");
        item2.setCost("0.6");
        item2.setNumber("5");

        //  Add items to the DAO
        testDao.addItem(item1.getCode(), item1);
        testDao.addItem(item2.getCode(), item2);

        // Remove first item - Kit-Kat
        Item removedItem = testDao.removeItem(item1.getCode());
        assertEquals(removedItem, item1, "The removed item should be Kit-Kat.");

        // Check remaining items
        List<Item> allItems = testDao.getAllItems();
        assertNotNull(allItems, "All items list should not be null.");
        assertEquals(1, allItems.size(), "All items list should only have 1 item.");
        assertFalse( allItems.contains(item1), "All students should NOT include Kit-Kat.");
        assertTrue( allItems.contains(item2), "All students should include Snickers.");

        // Remove second item - Snickers
        removedItem = testDao.removeItem(item2.getCode());
        assertEquals(removedItem, item2, "The removed item should be Snickers.");

        // Check remaining items - should be empty
        allItems = testDao.getAllItems();
        assertTrue( allItems.isEmpty(), "The retrieved list of items should be empty.");
        Item retrievedItem = testDao.removeItem(item1.getCode());
        assertNull(retrievedItem, "Kit-Kat was removed, should be null.");
        retrievedItem = testDao.removeItem(item2.getCode());
        assertNull(retrievedItem, "Snickers was removed, should be null.");
    }

    @Test
    public void testUpdate() throws Exception {
        // Create item
        String code = "A1";
        Item item = new Item(code);
        item.setName("Kit-Kat");
        item.setCost("0.55");
        item.setNumber("10");

        //  Add the item to the DAO, then retrieve it
        testDao.addItem(code, item);
        Item updatedItem = testDao.updateInventory(code, "9");
        assertEquals(updatedItem.getCode(), item.getCode(), "Item code should not be updated.");
        assertEquals(updatedItem.getName(), item.getName(), "Item name should not be updated.");
        assertEquals(updatedItem.getCost(), item.getCost(), "Item cost should not be updated.");
        assertNotEquals(updatedItem.getNumber(), item.getNumber(), "Item number should be updated from 10 to 9.");
    }
}