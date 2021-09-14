package com.vendingmachine.dao;

import com.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item onlyItem;

    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("A1");
        onlyItem.setName("Kit-Kat");
        onlyItem.setCost("0.55");
        onlyItem.setNumber("1");
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public Item addItem(String code, Item item) throws VendingMachinePersistenceException {
        if (code.equals(onlyItem.getCode())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item removeItem(String code) throws VendingMachinePersistenceException {
        if (code.equals(onlyItem.getCode())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item updateInventory(String code, String number) throws VendingMachinePersistenceException {
        if (code.equals(onlyItem.getCode())) {
            onlyItem.setNumber(number);
            return onlyItem;
        }  else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getItem(String code) throws VendingMachinePersistenceException {
        if (code.equals(onlyItem.getCode())) {
            return onlyItem;
        } else {
            return null;
        }
    }
}
