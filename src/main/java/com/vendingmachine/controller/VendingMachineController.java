package com.vendingmachine.controller;

import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;
import com.vendingmachine.service.*;
import com.vendingmachine.ui.VendingMachineView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        String selection = "";
        try {
            while (keepGoing) {

                listVendItems();
                selection = getSnackSelection();

                switch (selection) {
                    case "0":
                        restock();
                        break;
                    case "1":
                        keepGoing = false;
                        break;
                    default:
                        vend(selection);
                }

            }
            exitMessage();
        } catch (VendingMachinePersistenceException | VendingMachineNoItemInventoryException | VendingMachineInsufficientFundsException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private String getSnackSelection() {
        return view.displaySnackSelection();
    }

    private void restock() {
        boolean keepGoing = true;
        int selection = 0;
        try {
            while (keepGoing) {

                selection = view.displayRestockMenu();

                switch (selection) {
                    case 1:
                        addItem();
                        break;
                    case 2:
                        removeItem();
                        break;
                    case 3:
                        updateInventory();
                        break;
                    case 4:
                        viewItem();
                        break;
                    case 5:
                        listItems();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (VendingMachinePersistenceException | VendingMachineDuplicateIdException | VendingMachineDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void addItem() throws VendingMachinePersistenceException, VendingMachineDuplicateIdException, VendingMachineDataValidationException {
        view.displayCreateItemBanner();
        Item newItem = view.getNewItemInfo();
        service.addItem(newItem);
        view.displayCreateSuccessBanner();
    }

    private void removeItem() throws VendingMachinePersistenceException {
        view.displayRemoveItemBanner();
        String code = view.getCodeChoice();
        Item removedItem = service.removeItem(code);
        boolean success = view.checkCodeNull(removedItem);
        if (success == true){
            view.displayRemoveSuccessBanner();
        }
    }

    private void updateInventory() throws VendingMachinePersistenceException {
        view.displayUpdateBanner();
        String code = view.getCodeChoice();
        Item item = service.getItem(code);
        boolean success = view.checkCodeNull(item);
        String number = view.getUpdateNumber();
        if (success == true) {
            item = service.updateInventory(code, number);
            view.displayUpdateSuccess();
        }
    }

    private void viewItem() throws VendingMachinePersistenceException {
        view.displayDisplayItemBanner();
        String code = view.getCodeChoice();
        Item item = service.getItem(code);
        boolean success = view.checkCodeNull(item);
        if (success == true){
            view.displayItem(item);
        }
    }

    private void listItems() throws VendingMachinePersistenceException {
        view.displayListItemsBanner();
        List<Item> itemList = service.getAllItems();
        view.displayItemList(itemList);
    }

    public void listVendItems() throws VendingMachinePersistenceException {
        view.displayListItemsBanner();
        List<Item> itemList = service.getAllItems();
        view.displayVendItemList(itemList);
    }

    private void vend(String code) throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException, VendingMachineInsufficientFundsException {
        view.displayVendBanner();
        Item item = service.getItem(code);
        boolean success = view.checkCodeNull(item);
        if (success == true){
            view.displaySelectBanner();
            view.displayItem(item);
            int response = view.selectVend();
            if (response == 0) {
                ArrayList<String> coinsIn = getCoinsIn();
                HashMap<Item,ArrayList<String>> map = service.vendItem(code, coinsIn);

                Item vendedItem = null;
                ArrayList<String> changeGiven = null;
                for (Item i : map.keySet()) {
                    vendedItem = i;
                    changeGiven = map.get(i);
                }
                view.displayVendedBanner();
                view.displayItem(vendedItem);
                view.displayChangeCoins(changeGiven);
            }
        }
    }

    private ArrayList<String> getCoinsIn() {
        view.displayCoinsInBanner();
        ArrayList<String> coinsOut = new ArrayList<>();
        boolean keepGoing = true;
        int selection = 0;
        while (keepGoing) {
            selection = view.displayCoinMenu();
            switch (selection) {
                case 1:
                    coinsOut.add("TWOPOUND");
                    break;
                case 2:
                    coinsOut.add("ONEPOUND");
                    break;
                case 3:
                    coinsOut.add("FIFTYPENCE");
                    break;
                case 4:
                    coinsOut.add("TWENTYPENCE");
                    break;
                case 5:
                    coinsOut.add("TENPENCE");
                    break;
                case 6:
                    coinsOut.add("FIVEPENCE");
                    break;
                case 7:
                    coinsOut.add("TWOPENCE");
                    break;
                case 8:
                    coinsOut.add("ONEPENCE");
                    break;
                case 9:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                }
            view.displayCurrentCoins(coinsOut);
            }
        return coinsOut;
    }
}
