package com.vendingmachine.dao;

import com.vendingmachine.dto.Item;

import java.util.List;

public interface VendingMachineDao {

    Item addItem(String code, Item item) throws VendingMachinePersistenceException;

    Item removeItem(String code) throws VendingMachinePersistenceException;

    Item updateInventory(String code, String number) throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String code) throws VendingMachinePersistenceException;

}
