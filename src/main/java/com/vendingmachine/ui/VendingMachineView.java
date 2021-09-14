package com.vendingmachine.ui;

import com.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    //--------Menus----------------------------------------
    public String displaySnackSelection() {
        String input = io.readString(
                "To vend item from machine, enter item code." +
                "\nTo restock vending machine, enter '0'." +
                "\nTo exit vending machine, enter '1'." +
                "\n\nPlease enter your selection:");
        io.print("______________________");
        return input;
        }
    public int displayRestockMenu() {
        io.print("______________________");
        io.print("=== Restock Vending Machine ===");
        io.print("\t1) Add new snack");
        io.print("\t2) Remove snack");
        io.print("\t3) Update snack inventory");
        io.print("\t4) View snack");
        io.print("\t5) List all snacks");
        io.print("\t6) Exit");
        int input = io.readInt("Please choose from options:", 1, 6);
        io.print("______________________");
        return input;
    }
    public int displayCoinMenu() {
        io.print("\tAccepted Coins:");
        io.print("\t1) £2");
        io.print("\t2) £1");
        io.print("\t3) 50p");
        io.print("\t4) 20p");
        io.print("\t5) 10p");
        io.print("\t6) 5p");
        io.print("\t7) 2p");
        io.print("\t8) 1p");
        io.print("\t9) No more coins");
        int input = io.readInt("Please choose number to enter coin:", 1, 9);
        io.print("______________________");
        return input;
    }

    //--------Vend-----------------------------------------
    public void displayVendBanner() {
        io.print("=== Vend Item ===");
    }
    public void displaySelectBanner(){
        io.print("Item you have selected:\n");
    }
    public void displayVendedBanner() {
        io.print("Item successfully vended:");
    }
    public void displayChangeCoins(ArrayList<String> changeGiven) {
        io.print("Change Returned:\n");
        for (String coin : changeGiven) {
            io.print(coin);
        }
        io.print("\n");
    }
    public void displayVendItemList(List<Item>itemList) {
        io.print("______________________");
        for (Item currentItem : itemList) {
            io.print(currentItem.getCode() + ": " + currentItem.getName());
        }
        io.print("______________________\n");
    }
    public int selectVend() {
        int response = io.readInt("Vend item?\nType '0' for yes, '1' for no:", 0, 1);
        return response;
    }

    //--------Coins----------------------------------------
    public void displayCoinsInBanner() {
        io.print("=== Enter Coins ===");
    }
    public void displayCurrentCoins(ArrayList<String> coinsOut) {
        io.print("Current Selected Coins:\n");
        for (String coin : coinsOut) {
            io.print(coin);
        }
        io.print("\n");
    }

    //--------Add------------------------------------------
    public void displayCreateItemBanner() {
        io.print("=== Create Item ===");
    }
    public void displayCreateSuccessBanner() {
        io.print("Item successfully created. Please hit enter to continue.");
    }
    public Item getNewItemInfo() {
        String code = io.readString("Please Enter Item Code:");
        String name = io.readString("Please Enter Item Name:");
        String cost = io.readString("Please Enter Item Cost in Pounds:");
        String number = io.readString("Please Enter Item Inventory Number:");

        Item currentItem = new Item(code);
        currentItem.setName(name);
        currentItem.setCost(cost);
        currentItem.setNumber(number);
        return currentItem;
    }

    //--------Remove---------------------------------------
    public void displayRemoveItemBanner() {
        io.print("=== Remove Item ===");
    }
    public String getCodeChoice() {
        return io.readString("Please Enter the Item Code.");
    }
    public boolean checkCodeNull(Item itemRecord) {
        if(itemRecord == null){
            io.print("Item not stocked in vending machine.\n");
            return false;
        }else{
            return true;
        }
    }
    public void displayRemoveSuccessBanner() {
        io.print("Item successfully removed. Please hit enter to continue.");
    }

    //--------Updated--------------------------------------
    public void displayUpdateBanner() {
        io.print("=== Update Item ===");
    }
    public String getUpdateNumber() {
        String input = io.readString("Please Enter New Inventory Number:");
        return input;
    }
    public void displayUpdateSuccess() {
        io.print("Item successfully updated. Please hit enter to continue.");
    }

    //--------View-----------------------------------------
    public void displayDisplayItemBanner(){
        io.print("=== View Item ===");
    }
    public void displayItem(Item currentItem) {
        io.print("Code:\t" + currentItem.getCode());
        io.print("Name:\t" + currentItem.getName());
        io.print("Cost:\t£" + currentItem.getCost());
        io.print("Number:\t" + currentItem.getNumber());
        io.print("______________________");
    }

    //--------List-----------------------------------------
    public void displayListItemsBanner() {
        io.print("=== Vending Machine Items ===");
    }
    public void displayItemList(List<Item>itemList) {
        io.print("______________________");
        for (Item currentItem : itemList) {
            io.print("Code:\t" + currentItem.getCode());
            io.print("Name:\t" + currentItem.getName());
            io.print("Cost:\t£" + currentItem.getCost());
            io.print("Number:\t: " + currentItem.getNumber());
            io.print("\n");
        }
        io.print("______________________\n");
        io.readString("Please hit enter to continue.");
    }

    //--------Other----------------------------------------
    public void displayExitBanner() {
        io.print("Good Bye!");
    }
    public void displayErrorMessage(String errorMsg) {
        io.print("\n=== ERROR ===");
        io.print(errorMsg);
    }
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command");
    }

}
