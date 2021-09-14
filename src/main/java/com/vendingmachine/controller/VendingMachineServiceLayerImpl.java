package com.vendingmachine.controller;

import com.vendingmachine.dao.VendingMachineAuditDao;
import com.vendingmachine.dao.VendingMachineDao;
import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;
import com.vendingmachine.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private Change change;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao, Change change) {
        this.dao = dao;
        this.auditDao = auditDao;
        this.change = change;
    }

    @Override
    public void addItem(Item item) throws VendingMachinePersistenceException, VendingMachineDataValidationException, VendingMachineDuplicateIdException {

        if (dao.getItem(item.getCode()) != null) {
            throw new VendingMachineDuplicateIdException(
                    "ERROR: Could not create item.  Item code " + item.getCode() + " already exists");
        }

        validateItemData(item);
        dao.addItem(item.getCode(), item);
        auditDao.writeAuditEntry("Item " + item.getCode() + " CREATED.");

    }

    @Override
    public Item removeItem(String code) throws VendingMachinePersistenceException {
        Item removedItem = dao.removeItem(code);
        auditDao.writeAuditEntry("Student " + code + " REMOVED.");
        return removedItem;
    }

    @Override
    public Item updateInventory(String code, String number) throws VendingMachinePersistenceException {
        Item updatedItem = dao.updateInventory(code, number);
        auditDao.writeAuditEntry("Item " + code + " number UPDATED to" + number + ".");
        return updatedItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String code) throws VendingMachinePersistenceException {
        return dao.getItem(code);
    }

    @Override
    public HashMap<Item,ArrayList<String>> vendItem(String code, ArrayList<String> coinsIn) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        HashMap<Item,ArrayList<String>> map = new HashMap<Item,ArrayList<String>>();

        // make sure item is in inventory
        Item requestedItem = dao.getItem(code);
        int number = Integer.parseInt(requestedItem.getNumber());

        if (number == 0) {
            throw new VendingMachineNoItemInventoryException(
                    "ERROR: Item could not be vended. Item " + code + " has 0 in inventory.");
        }

        // make sure enough money is given
        BigDecimal cost = new BigDecimal(requestedItem.getCost());
        BigDecimal paid = calcCoinSum(coinsIn);
        int enough = paid.compareTo(cost);
        if (enough == -1) {
            throw new VendingMachineInsufficientFundsException(
                    "ERROR: Item could not be vended. Insufficient funds!");
        }

        // get change
        ArrayList<String> changeGiven = change.calcChange(paid.subtract(cost));

        // decrease inventory of item
        int n = number - 1;
        Item vendedItem = updateInventory(code, String.valueOf(n));

        // return vended item
        auditDao.writeAuditEntry("Item " + code + " VENDED.");

        map.put(vendedItem, changeGiven);
        return map;
    }

    private BigDecimal calcCoinSum(ArrayList<String> coinsIn) {
        BigDecimal sum = new BigDecimal("0");
        for (int i=0; i<coinsIn.size(); i++) {
            Change.coins coin = Change.coins.valueOf(coinsIn.get(i));
            sum = sum.add(change.getCoinValue(coin));
        }

        return sum;
    }

    private void validateItemData(Item item) throws VendingMachineDataValidationException {
        if (item.getName() == null
                || item.getName().trim().length() == 0
                || item.getCost() == null
                || item.getCost().trim().length() == 0
                || item.getNumber() == null
                || item.getNumber().trim().length() == 0) {

            throw new VendingMachineDataValidationException(
                    "ERROR: All fields [Name, Cost, Number] are required.");
        }

        try {
            BigDecimal cost = new BigDecimal(item.getCost());
        } catch (NumberFormatException e) {
            throw new VendingMachineDataValidationException(
                    "ERROR: Cost should be a numerical value. Do not include any money symbols.");
        }

        try {
            int number = Integer.parseInt(item.getNumber());
        } catch (NumberFormatException e) {
            throw new VendingMachineDataValidationException(
                    "ERROR: Inventory number should be a numerical value. Number should be a whole value.");
        }

        BigDecimal cost = new BigDecimal(item.getCost());
        BigDecimal x = new BigDecimal("0");
        int compare = x.compareTo(cost);
        if( compare == 0 ){
            throw new VendingMachineDataValidationException(
                    "ERROR: Cost must be more than 0.");
        }
        else if( compare == 1 ) {
            throw new VendingMachineDataValidationException(
                    "ERROR: Cost cannot be negative.");
        }

        int number = Integer.parseInt(item.getNumber());
        if (number < 0) {
            throw new VendingMachineDataValidationException(
                    "ERROR: Number in inventory cannot be less than 0.");
        }
        if (number > 100) {
            throw new VendingMachineDataValidationException(
                    "ERROR: Number in inventory cannot be more than 100.");
        }

    }

}
