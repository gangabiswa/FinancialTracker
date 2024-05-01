package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String type;
    private String vendor;
    private  Double price;

    public Transaction(LocalDate date, LocalTime time, String vendor, String type, double price) {
        this.time = time;
        this.vendor = vendor;
        this.price = price;
        this.type = type;
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getVendor() {
        return vendor;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "vendor='" + vendor + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


}







