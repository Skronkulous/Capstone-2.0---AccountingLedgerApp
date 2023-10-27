package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;


public class BankingApp {
    private List<Transaction> ledger = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        BankingApp app = new BankingApp();
        app.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayHomeScreen();
            String choice = scanner.nextLine();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    displayLedger();
                    break;
                case "X":
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayHomeScreen() {
        System.out.println("Home Screen Options:");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter your choice: ");
    }

    private void addDeposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Save deposit information to a CSV file with date and time
        try {
            FileWriter writer = new FileWriter("ledger.csv", true);
            writer.append("Deposit," + amount + "," + now.format(formatter) + "\n");
            writer.close();
            System.out.println("Deposit added to the ledger.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makePayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Save payment information to a CSV file with date and time
        try {
            FileWriter writer = new FileWriter("ledger.csv", true);
            writer.append("Payment," + amount + "," + now.format(formatter) + "\n");
            writer.close();
            System.out.println("Payment added to the ledger.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayLedger() {
        try {
            FileReader reader = new FileReader("ledger.csv");
            BufferedReader br = new BufferedReader(reader);
            String line;
            List<String> ledgerEntries = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ledgerEntries.add(line);
            }
            br.close();

            // Display ledger entries with date and time
            for (int i = ledgerEntries.size() - 1; i >= 0; i--) {
                String[] parts = ledgerEntries.get(i).split(",");
                String type = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String timestamp = parts[1];
                System.out.println(type + " - " + amount + " - " + timestamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}