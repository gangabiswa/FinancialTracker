package com.pluralsight;

import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim());
                    String type = parts[2].trim();
                    String vendor = parts[3].trim();
                    double price = Double.parseDouble(parts[4].trim());
                    transactions.add(new Transaction(date, time, type, vendor, price));

                }
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println("Error loading inventory:" + e.getMessage());
        }

    }

    private static void addDeposit(Scanner scanner) {
        System.out.println("Enter the date (yyyy-MM-dd) ");
        String date = scanner.nextLine().trim();

        System.out.println("Enter the time (HH:mm:ss)");
        String time = scanner.nextLine().trim();
        LocalDate date1 = null;
        LocalTime time1 = null;

        try {
            date1 = LocalDate.parse(date, DATE_FORMATTER);
            time1 = LocalTime.parse(time, TIME_FORMATTER);


        } catch (DateTimeParseException e) {
            System.out.println(" Invalid date/time format. please use yyyy-MM-dd HH:mm:ss ");

        }
        System.out.println("Enter the type");
        String type = scanner.nextLine();


        System.out.println("Enter the vendor:");
        String vendor = scanner.nextLine().trim();


        System.out.println("Enter the amount:");
        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println(" Amount Deposit must be positive");
            }
        } catch (NumberFormatException e) {
            System.out.println("invalid amount format. please enter the valid number ");
            return;
        }

        Transaction deposit = new Transaction(date1, time1, type, vendor, amount);
        transactions.add(deposit);

        //write deposit info to csv file.
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            buffwriter.write(date1.toString() + "|" + time1.toString() + "|" + type + "|" + vendor + "|" + amount);
            System.out.println(" Deposit added successfully");
            buffwriter.close();
        } catch (IOException e) {
            System.out.println("error writing to transaction file");


        }
    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Enter the date (yyyy-MM-dd):");
        String datestring = scanner.nextLine();
        LocalDate date = LocalDate.parse(datestring, DATE_FORMATTER);
        LocalDate date2 = null;

        System.out.println("enter the time (HH:mm:ss)");
        String timestring = scanner.nextLine();
        LocalTime time = LocalTime.parse(timestring, TIME_FORMATTER);
        LocalTime time2 = null;

        System.out.println("Enter the type");
        String type = scanner.nextLine();

        System.out.println("Enter the vendor");
        String vendor = scanner.nextLine();

        System.out.println("Enter the amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();


        if (amount <= 0) {
            System.out.println("amount must be positive number");
        } else {
            amount = -amount;
            Transaction payment = new Transaction(date, time, vendor, type, amount);
            transactions.add(payment);
            System.out.println("payment added successfully");
        }


        //write deposit info to transaction file.
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            buffwriter.write(date.toString() + "|" + time.toString() + "|" + type + "|" + vendor + "|" + amount);
            buffwriter.close();
        } catch (IOException e) {
            System.out.println("error writing to transaction file");

        }

    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // display table headers
        System.out.println("  Date  |   Time   |  Type   |   Vendor  |  Amount");
        for (Transaction tranz : transactions) {
            LocalDate date = tranz.getDate();
            LocalTime time = tranz.getTime();
            String vendor = tranz.getVendor();
            String type = tranz.getType();
            double amount = tranz.getPrice();

            System.out.printf(" %s | %s | %s-10s | %s | %.2f%n ", date, time, vendor, type, amount);
        }
    }

    private static void displayDeposits() {
        System.out.println(" Date   |   time   |   type   |    vendor   |   amount   ");

        for (Transaction transac : transactions) {
            // is a conditional statement that checks if the amount of a transaction is greater than zero.
            if (transac.getPrice() > 0) {
                //Extract transaction details
                LocalDate date = transac.getDate();
                LocalTime time = transac.getTime();
                String vendor = transac.getVendor();
                String type = transac.getType();
                double amount = transac.getPrice();

                System.out.printf("   %s    |   %s   |    %s   |   %.2f%n   ", date, time, type, vendor, amount);
            }
        }
    }

    private static void displayPayments() {

        System.out.println(" Date   |   time   |   type   |    vendor   |   amount   ");

        for (Transaction transac : transactions) {
            if (transac.getPrice() < 0) {

                LocalDate date = transac.getDate();
                LocalTime time = transac.getTime();
                String vendor = transac.getVendor();
                String type = transac.getType();
                double amount = transac.getPrice();

                System.out.printf("   %s    |   %s   |    %s   |   %.2f%n   ", date, time, type, vendor, amount);
            }
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    LocalDate today = LocalDate.now();
                    LocalDate startDate = today.withDayOfMonth(1);
                    LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());
                    filterTransactionsByDate(startDate, endDate);
                    break;

                case "2":
                    LocalDate lastMonth = LocalDate.now().minusMonths(1);
                    LocalDate startDate1 = lastMonth.withDayOfMonth(1);
                    LocalDate endDate1 = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
                    filterTransactionsByDate(startDate1, endDate1);
                    break;

                case "3":
                    LocalDate today1 = LocalDate.now();
                    LocalDate startOfYear = today1.withDayOfYear(1);
                    LocalDate endOfYear = today1.withDayOfYear(today1.lengthOfYear());
                    filterTransactionsByDate(startOfYear, endOfYear);
                    break;

                case "4":
                    LocalDate lastYear = LocalDate.now().minusYears(1);
                    LocalDate startOfLastYear = lastYear.withDayOfYear(1);
                    LocalDate endOfLastYear = lastYear.withDayOfYear(lastYear.lengthOfYear());
                    filterTransactionsByDate(startOfLastYear, endOfLastYear);
                    break;

                case "5":
                    System.out.println(" enter a vendor name ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);

                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        boolean foundTransaction = false;
        for (Transaction transaction : transactions) {
            boolean after = !transaction.getDate().isBefore(startDate);
            boolean before = !transaction.getDate().isAfter(endDate);

            if (after && before) {
                double positive = Math.abs(transaction.getPrice());
                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getType() + "|" + transaction.getVendor() + "|" + transaction.getPrice() + "|");
                foundTransaction = true;

            }
        }
        if (!foundTransaction) {
            System.out.println("No result Found try again. ");
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        boolean foundTranscation = false;
        for (Transaction transaction : transactions) {
            if (vendor.equals(transaction.getVendor())) {
                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getType() + "|" + transaction.getVendor() + "|" + transaction.getPrice() + "|");
                foundTranscation = true;


            }
        }
        if (!foundTranscation) {
            System.out.println("no vendor found, please try again");

        }
    }
}