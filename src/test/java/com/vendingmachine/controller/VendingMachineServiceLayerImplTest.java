package com.vendingmachine.controller;

import com.vendingmachine.dao.*;
import com.vendingmachine.dto.Item;
import com.vendingmachine.service.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        Change change = new Change();

        service = new VendingMachineServiceLayerImpl(dao, auditDao, change);
    }

    @Test
    public void testAddValidItem() {
        Item item = new Item("B1");
        item.setName("Snickers");
        item.setCost("0.6");
        item.setNumber("5");

        try {
            service.addItem(item);
        } catch (VendingMachineDuplicateIdException
                | VendingMachineDataValidationException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Item was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testDuplicateCodeItem() {
        Item item = new Item("A1");
        item.setName("Snickers");
        item.setCost("0.6");
        item.setNumber("5");

        try {
            service.addItem(item);
        } catch (VendingMachineDataValidationException
                | VendingMachinePersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineDuplicateIdException e){
            return;
        }
    }

    @Test
    public void testAddInvalidItem() {
        Item item = new Item("B1");
        item.setName("Snickers");
        item.setCost("");
        item.setNumber("5");

        try {
            service.addItem(item);
            fail("Expected ValidationException was not thrown.");
        } catch (VendingMachineDuplicateIdException
                | VendingMachinePersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineDataValidationException e){
            return;
        }
    }

    @Test
    public void testRemoveItem() throws Exception {
        Item cloneItem = new Item("A1");
        cloneItem.setName("Kit-Kat");
        cloneItem.setCost("0.55");
        cloneItem.setNumber("1");

        Item shouldBeKitKat = service.removeItem("A1");
        assertNotNull(shouldBeKitKat, "Removing 'A1' should not be null.");
        assertEquals(cloneItem, shouldBeKitKat, "Item removed with code 'A1' should be KitKat");

        Item shouldBeNull = service.removeItem("C1");
        assertNull(shouldBeNull, "Item removed with code 'C1' should be null");
    }

    @Test
    public void testUpdateItem() throws Exception {
        Item cloneItem = new Item("A1");
        cloneItem.setName("Kit-Kat");
        cloneItem.setCost("0.55");
        cloneItem.setNumber("1");

        Item updatedItem = service.updateInventory("A1", "3");
        assertNotNull(updatedItem, "Updated Item  with code 'A1' should not be null.");
        assertNotEquals(cloneItem, updatedItem, "Item with code 'A1' should have been updated.");
        assertNotEquals(cloneItem.getNumber(), updatedItem.getNumber(), "Inventory number should have been changed from 1 to 3.");
    }

    @Test
    public void testGetAllItems() throws Exception {
        Item cloneItem = new Item("A1");
        cloneItem.setName("Kit-Kat");
        cloneItem.setCost("0.55");
        cloneItem.setNumber("1");

        assertEquals(1, service.getAllItems().size(), "Should only have one item.");
        assertTrue(service.getAllItems().contains(cloneItem), "The one item should be Kit-Kat.");
    }

    @Test
    public void testGetItem() throws Exception {
        Item cloneItem = new Item("A1");
        cloneItem.setName("Kit-Kat");
        cloneItem.setCost("0.55");
        cloneItem.setNumber("1");

        Item shouldBeKitKat = service.getItem("A1");
        assertNotNull(shouldBeKitKat, "Getting item with code 'A1' should not be null.");
        assertEquals(cloneItem, shouldBeKitKat, "Item with code 'A1' should be Kit-Kat.");

        Item shouldBeNull = service.getItem("C1");
        assertNull(shouldBeNull, "Getting item with code 'C1' should be null.");
    }

    @Test
    public void testValidVendItem() {
        // Coins in to vending machine = £1
        ArrayList<String> coinsIn = new ArrayList<String>();
        coinsIn.add("ONEPOUND");

        // Expected coins out from vending machine = 45p
        ArrayList<String> coinsOut = new ArrayList<String>();
        coinsIn.add("TWENTYPENCE");
        coinsIn.add("TWENTYPENCE");
        coinsIn.add("FIVEPENCE");

        try {
            Item startItem = service.getItem("A1");
            int n = Integer.parseInt(startItem.getNumber());

            HashMap<Item,ArrayList<String>> map = service.vendItem("A1", coinsIn);

            Item vendedItem = null;
            ArrayList<String> changeGiven = null;
            for (Item i : map.keySet()) {
                vendedItem = i;
                changeGiven = map.get(i);
            }

            assertNotNull(vendedItem, "Vended item should not be null.");
            assertNotNull(changeGiven, "Change should not be null.");
            assertEquals(coinsOut, changeGiven, "Change given should be 45p.");

            int number = Integer.parseInt(vendedItem.getNumber());
            assertEquals(n-1, number, "Vended item should reduce in inventory by 1. Was 1, now should be 0.");

        } catch (VendingMachinePersistenceException
                | VendingMachineInsufficientFundsException
                | VendingMachineNoItemInventoryException e) {
            // ASSERT
            fail("Item was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testVendNoInventory() {
        // Coins in to vending machine = £1
        ArrayList<String> coinsIn = new ArrayList<String>();
        coinsIn.add("ONEPOUND");

        try {
            HashMap<Item,ArrayList<String>> map = service.vendItem("A1", coinsIn);  // Vend last item- inventory should now be 0
            map = service.vendItem("A1", coinsIn);                                  // Vend again
            fail("Expected NoInventory Exception was not thrown.");

        } catch (VendingMachinePersistenceException
                | VendingMachineInsufficientFundsException e) {
            // ASSERT
            fail("Item was valid. No exception should have been thrown.");
        } catch (VendingMachineNoItemInventoryException e) {
            return;
        }
    }

    @Test
    public void testVendInsufficientFunds() {
        // Coins in to vending machine = 50p
        ArrayList<String> coinsIn = new ArrayList<String>();
        coinsIn.add("FIFTYPENCE");

        try {
            HashMap<Item,ArrayList<String>> map = service.vendItem("A1", coinsIn);
            fail("Expected InsufficientFunds Exception was not thrown.");

        } catch (VendingMachinePersistenceException
                | VendingMachineNoItemInventoryException e) {
            // ASSERT
            fail("Item was valid. No exception should have been thrown.");
        } catch (VendingMachineInsufficientFundsException e) {
            return;
        }
    }
}