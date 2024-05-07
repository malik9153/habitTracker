package com.example.demo.utilities;

public class ScannerPrinter {

    public void welcomeMessage() {
        System.out.println("Welcome to the Habit Tracker!");
    }

    public void displayMenu() {
        System.out.println("\nHabit Tracker Menu:");
        System.out.println("1. Add a habit");
        System.out.println("2. Remove a habit");
        System.out.println("3. Log a habit for today");
        System.out.println("4. Display habits");
        System.out.println("5. Display logs for a habit");
        System.out.println("6. Set a reminder on your calendar");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    public void SignIn() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");
    }
}
