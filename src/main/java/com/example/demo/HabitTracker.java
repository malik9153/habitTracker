package com.example.demo;

import com.example.demo.utilities.loadHabitsFromFile;
import com.example.demo.utilities.saveHabitsToFile;

import java.util.HashMap;
import java.util.Scanner;

public class HabitTracker {
    public HashMap<String, Habit> habits;
    private static boolean loggedIn = false;
    private User currentUser;
    private Scanner scanner;

    public HabitTracker() {
        habits = new HashMap<>();
        this.currentUser = new User();
        this.scanner = new Scanner(System.in);
    }

    public void addHabit(String habitName) {
        Habit newHabit = new Habit(0, habitName);
        habits.put(habitName, newHabit);
        System.out.println("Habit added: " + habitName);
    }

    public void removeHabit(String habitName) {
        Habit habitToRemove = habits.remove(habitName);
        if (habitToRemove != null) {
            System.out.println("Habit removed: " + habitToRemove.getName());
        } else {
            System.out.println("Habit not found: " + habitName);
        }
    }

    public void displayHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits to display.");
        } else {
            System.out.println("Your habits:");
            for (Habit habit : habits.values()) {
                System.out.println("- " + habit.getLogNumber() + ". " + habit.getName());
            }
        }
    }
    private void welcomeMessage() {
        System.out.println("Welcome to the Habit Tracker!");
    }
    private void handleLoginOrRegistration(HabitTracker habitTracker) {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                currentUser.login();
                habitTracker.habits = loadHabitsFromFile.loadHabits(currentUser.getUniqueId(), habits);
                break;
            case 2:
                currentUser.register();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayMenu() {
        System.out.println("\nHabit Tracker Menu:");
        System.out.println("1. Add a habit");
        System.out.println("2. Remove a habit");
        System.out.println("3. Log a habit for today");
        System.out.println("4. Display habits");
        System.out.println("5. Display logs for a habit");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    private void addingHabit(HabitTracker habitTracker) {
        System.out.print("Enter the habit to add: ");
        String habitName = scanner.nextLine();
        addHabit(habitName);
    }
    private void removingHabit(HabitTracker habitTracker) {
        System.out.print("Enter the name of the habit to remove: ");
        String habitNameToRemove = scanner.nextLine();
        habitTracker.removeHabit(habitNameToRemove);
    }
    private void LoggingHabit(HabitTracker habitTracker) {
        System.out.print("Enter the name of the habit to log: ");
        String habitNameToLog = scanner.nextLine();
        Habit habitToLog = habitTracker.habits.get(habitNameToLog);
        if (habitToLog != null) {
            habitToLog.addLog();
            System.out.println("Habit logged for today: " + habitToLog.getName());
        } else {
            System.out.println("Habit not found with number: " + habitToLog);
        }
    }
    private void displayingLogs(HabitTracker habitTracker) {
        System.out.print("Enter the number of the habit to display logs: ");
        String habitNameForLogs = (scanner.nextLine());
        Habit habitForLogs = habitTracker.habits.get(habitNameForLogs);
        if (habitForLogs != null) {
            habitForLogs.displayLogs();
        } else {
            System.out.println("Habit not found : " + habitNameForLogs);
        }
    }


    public void handleUserActions(HabitTracker habitTracker) {
        String choice;

        do {
            displayMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addingHabit(habitTracker);
                    break;
                case "2":
                    removingHabit(habitTracker);
                    break;
                case "3":
                    LoggingHabit(habitTracker);
                    break;
                case "4":
                    habitTracker.displayHabits();
                    break;
                case "5":
                    displayingLogs(habitTracker);
                    break;
                case "6":
                    saveHabitsToFile.saveHabits(currentUser.getUniqueId(), habitTracker);
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("6"));

        scanner.close();
    }

    public void tracking() {
        HabitTracker habitTracker = new HabitTracker();
        welcomeMessage();
        while (!loggedIn) {
            handleLoginOrRegistration(habitTracker);
        }
            handleUserActions(habitTracker);
    }
}

