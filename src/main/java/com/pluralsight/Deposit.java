package com.pluralsight;

public class Deposit extends Transaction{
    private String date;
    private String time;
    private String type;
    private String vendor;
    private  double price;

    public Deposit(String price, String vendor, String date, String time, double type) {
        super(price, vendor, date, time, type);
    }

}
