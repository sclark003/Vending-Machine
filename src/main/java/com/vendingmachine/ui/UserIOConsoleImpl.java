package com.vendingmachine.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    final private Scanner myScanner = new Scanner(System.in);

    public void print(String msg){
        System.out.println(msg);
    }

    public double readDouble(String prompt){
        System.out.println(prompt);
        double response = myScanner.nextDouble();
        myScanner.nextLine();
        return response;
    }

    public double readDouble(String prompt, double min, double max){
        double response;
        do{
            System.out.println(prompt);
            response = myScanner.nextDouble();
            myScanner.nextLine();
        }
        while(response<min || response>max);
        return response;
    }

    public float readFloat(String prompt){
        System.out.println(prompt);
        float response = myScanner.nextFloat();
        myScanner.nextLine();
        return response;
    }

    public float readFloat(String prompt, float min, float max){
        float response;
        do{
            System.out.println(prompt);
            response = myScanner.nextFloat();
            myScanner.nextLine();
        }
        while(response<min || response>max);
        return response;
    }

    public int readInt(String prompt){
        System.out.println(prompt);
        int response = myScanner.nextInt();
        myScanner.nextLine();
        return response;
    }

    public int readInt(String prompt, int min, int max){
        int response;
        do{
            System.out.println(prompt);
            response = myScanner.nextInt();
            myScanner.nextLine();
        }
        while(response<min || response>max);
        return response;
    }

    public long readLong(String prompt){
        System.out.println(prompt);
        long response = myScanner.nextLong();
        myScanner.nextLine();
        return response;
    }

    public long readLong(String prompt, long min, long max){
        long response;
        do{
            System.out.println(prompt);
            response = myScanner.nextLong();
            myScanner.nextLine();
        }
        while(response<min || response>max);
        return response;
    }

    public String readString(String prompt){
        System.out.println(prompt);
        String response = myScanner.nextLine();
        return response;
    }
}

