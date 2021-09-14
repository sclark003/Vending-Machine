package com.vendingmachine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChangeTest {

    private Change change = new Change();

    public ChangeTest() {
    }

    @Test
    public void test388() throws Exception {
        BigDecimal change_in = new BigDecimal("388");
        ArrayList<String> coins_out = new ArrayList<String>();
        coins_out.add("TWOPOUND");
        coins_out.add("ONEPOUND");
        coins_out.add("FIFTYPENCE");
        coins_out.add("TWENTYPENCE");
        coins_out.add("TENPENCE");
        coins_out.add("FIVEPENCE");
        coins_out.add("TWOPENCE");
        coins_out.add("ONEPENCE");

        ArrayList<String> coins = change.calcChange(change_in);
        assertEquals(coins_out, coins, "Expected " + coins_out);
    }

    @Test
    public void test0() throws Exception {
        BigDecimal change_in = new BigDecimal("0");
        ArrayList<String> coins_out = new ArrayList<String>();

        ArrayList<String> coins = change.calcChange(change_in);
        assertEquals(coins_out, coins, "Expected " + coins_out);
    }

    @Test
    public void test450() throws Exception {
        BigDecimal change_in = new BigDecimal("450");
        ArrayList<String> coins_out = new ArrayList<String>();
        coins_out.add("TWOPOUND");
        coins_out.add("TWOPOUND");
        coins_out.add("FIFTYPENCE");

        ArrayList<String> coins = change.calcChange(change_in);
        assertEquals(coins_out, coins, "Expected " + coins_out);
    }

    @Test
    public void test33() throws Exception {
        BigDecimal change_in = new BigDecimal("33");
        ArrayList<String> coins_out = new ArrayList<String>();
        coins_out.add("TWENTYPENCE");
        coins_out.add("TENPENCE");
        coins_out.add("TWOPENCE");
        coins_out.add("ONEPENCE");

        ArrayList<String> coins = change.calcChange(change_in);
        assertEquals(coins_out, coins, "Expected " + coins_out);
    }

    @Test
    public void test1() throws Exception {
        BigDecimal change_in = new BigDecimal("1");
        ArrayList<String> coins_out = new ArrayList<String>();
        coins_out.add("ONEPENCE");

        ArrayList<String> coins = change.calcChange(change_in);
        assertEquals(coins_out, coins, "Expected " + coins_out);
    }

}