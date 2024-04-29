package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String type;
    private String vendor;
    private  Double price;

    public Transaction(String price, String vendor, String date, String time, double type) {
        this.price = Double.valueOf(price);
        this.vendor = vendor;
        this.date = date;
        this.time = time;
        this.type = String.valueOf(type);
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getVendor() {return vendor;}

    public void setVendor(String vendor) {this.vendor = vendor;}

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}
}


