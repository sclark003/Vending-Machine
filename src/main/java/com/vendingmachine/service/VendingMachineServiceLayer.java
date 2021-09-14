package com.vendingmachine.service;

import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface VendingMachineServiceLayer {

    void addItem(Item item) throws
            VendingMachinePersistenceException,
            VendingMachineDataValidationException,
            VendingMachineDuplicateIdException;

    Item removeItem(String code) throws
            VendingMachinePersistenceException;

    Item updateInventory(String code, String number) throws
            VendingMachinePersistenceException;

    List<Item> getAllItems() throws
            VendingMachinePersistenceException;

    Item getItem(String code) throws
            VendingMachinePersistenceException;

    HashMap<Item,ArrayList<String>> vendItem(String code, ArrayList<String> coins) throws
            VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineNoItemInventoryException;

}


// If the user selects an item that costs more than the amount the user put into the vending machine,
// the program should display a message indicating insufficient funds and then redisplay the amount the user had put into the machine.
//If the user selects an item that costs equal to or less than the amount of money that the user put in the vending machine,
// the program should display the change returned to the user. Change must be displayed as the number of quarters, dimes, nickels, and pennies returned to the user.
// When an item is vended, the program must update the inventory level appropriately. If the machine runs out of an item, it should no longer be available as an option to the user.
// However, the items that have an inventory level of zero must still be read from and written to the inventory file and stored in memory.