package com.vendingmachine.dao;

import com.vendingmachine.dto.Item;

import java.io.*;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private Map<String, Item> items = new HashMap<>();
    private final String INVENTORY_FILE;
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }

    @Override
    public Item addItem(String code, Item item) throws VendingMachinePersistenceException {
        loadInventory();
        Item newItem = items.put(code, item);
        writeInventory();
        return newItem;
    }

    @Override
    public Item removeItem(String code) throws VendingMachinePersistenceException {
        loadInventory();
        Item removedItem = items.remove(code);
        writeInventory();
        return removedItem;
    }

    @Override
    public Item updateInventory(String code, String number) throws VendingMachinePersistenceException {
        loadInventory();
        Item updateItem = items.get(code);
        items.remove(code);
        updateItem.setNumber(number);
        items.put(code, updateItem);
        writeInventory();
        return updateItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList(items.values());
    }

    @Override
    public Item getItem(String code) throws VendingMachinePersistenceException {
        loadInventory();
        return items.get(code);
    }

    private Item unmarshallItem(String itemAsText){

        String[] itemTokens = itemAsText.split(DELIMITER);
        String code = itemTokens[0];
        Item itemFromFile = new Item(code);
        itemFromFile.setName(itemTokens[1]);
        itemFromFile.setCost(itemTokens[2]);
        itemFromFile.setNumber(itemTokens[3]);
        return itemFromFile;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load inventory data into memory.", e);
        }

        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getCode(), currentItem);
        }
        scanner.close();
    }

    private String marshallItem(Item anItem){

        String itemAsText = anItem.getCode() + DELIMITER;
        itemAsText += anItem.getName() + DELIMITER;
        itemAsText += anItem.getCost() + DELIMITER;
        itemAsText += anItem.getNumber();
        return itemAsText;
    }

    private void writeInventory() throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

}
