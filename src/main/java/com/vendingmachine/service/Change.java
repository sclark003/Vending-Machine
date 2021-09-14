package com.vendingmachine.service;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Change {

    private BigDecimal change;
    private BigDecimal twoPounds = new BigDecimal("2");
    private BigDecimal onePound = new BigDecimal("1");
    private BigDecimal fiftyPence = new BigDecimal("0.5");
    private BigDecimal twentyPence = new BigDecimal("0.2");
    private BigDecimal tenPence = new BigDecimal("0.1");
    private BigDecimal fivePence = new BigDecimal("0.05");
    private BigDecimal twoPence = new BigDecimal("0.02");
    private BigDecimal onePence= new BigDecimal("0.01");

    public Change() {
    }

    public enum coins {
        TWOPOUND, ONEPOUND, FIFTYPENCE, TWENTYPENCE, TENPENCE, FIVEPENCE, TWOPENCE, ONEPENCE
    }

    public ArrayList<String> calcChange(BigDecimal change) {

        BigDecimal x = new BigDecimal("0");
        System.out.println("A: " + change.toString());
        ArrayList<String> coins = new ArrayList<String>();
        while(change.subtract(this.twoPounds).floatValue() >= 0) {
            coins.add("TWOPOUND");
            change = change.subtract(this.twoPounds);
            System.out.println("B: " + change.toString());
        }

        while(change.subtract(this.onePound).floatValue() >= 0) {
            coins.add("ONEPOUND");
            change = change.subtract(this.onePound);
            System.out.println("C: " + change.toString());
        }

        while(change.subtract(this.fiftyPence).floatValue() >= 0) {
            coins.add("FIFTYPENCE");
            change = change.subtract(this.fiftyPence);
            System.out.println("D: " + change.toString());
        }

        while(change.subtract(this.twentyPence).floatValue() >= 0) {
            coins.add("TWENTYPENCE");
            change = change.subtract(this.twentyPence);
            System.out.println("E: " + change.toString());
        }

        while(change.subtract(this.tenPence).floatValue() >= 0) {
            coins.add("TENPENCE");
            change = change.subtract(this.tenPence);
            System.out.println("F: " + change.toString());
        }

        while(change.subtract(this.fivePence).floatValue() >= 0) {
            coins.add("FIVEPENCE");
            change = change.subtract(this.fivePence);
            System.out.println("G: " + change.toString());
        }

        while(change.subtract(this.twoPence).floatValue() >= 0) {
            coins.add("TWOPENCE");
            change = change.subtract(this.twoPence);
            System.out.println("H: " + change.toString());
        }

        while(change.subtract(this.onePence).floatValue() >= 0) {
            coins.add("ONEPENCE");
            change = change.subtract(this.onePence);
            System.out.println("I: " + change.toString());
        }

        return coins;
    }

    public BigDecimal getCoinValue(coins coin) {
        BigDecimal value;
        switch (coin) {
            case TWOPOUND:
                value = getTwoPounds();
                break;
            case ONEPOUND:
                value = getOnePound();
                break;
            case FIFTYPENCE:
                value = getFiftyPence();
                break;
            case TWENTYPENCE:
                value = getTwentyPence();
                break;
            case TENPENCE:
                value = getTenPence();
                break;
            case FIVEPENCE:
                value = getFivePence();
                break;
            case TWOPENCE:
                value = getTwoPence();
                break;
            case ONEPENCE:
                value = getOnePence();
                break;
            default:
                value = null;
        }

        return value;
    }

    public BigDecimal getTwoPounds() {
        return this.twoPounds;
    }

    public BigDecimal getOnePound() {
        return this.onePound;
    }

    public BigDecimal getFiftyPence() {
        return this.fiftyPence;
    }

    public BigDecimal getTwentyPence() {
        return this.twentyPence;
    }

    public BigDecimal getTenPence() {
        return this.tenPence;
    }

    public BigDecimal getFivePence() {
        return this.fivePence;
    }

    public BigDecimal getTwoPence() {
        return this.twoPence;
    }

    public BigDecimal getOnePence() {
        return this.onePence;
    }

}


