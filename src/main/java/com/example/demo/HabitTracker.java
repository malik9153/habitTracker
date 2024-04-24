package com.example.demo;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HabitTracker {
    private HashMap<Integer, Habit> habits;
    private int nextHabitNumber;
    private static boolean loggedIn = false;

    private static User currentUser ;

    public HabitTracker() {
        habits = new HashMap<>();
        nextHabitNumber = 1;
    }

    public void addHabit(Habit habit) {
        habits.put(habit.getNumber(), habit);
        System.out.println("Habit added: " + habit.getName());
    }

    public void removeHabit(int habitNumber) {
        Habit habitToRemove = habits.remove(habitNumber);
        if (habitToRemove != null) {
            System.out.println("Habit removed: " + habitToRemove.getName());
        } else {
            System.out.println("Habit not found with number: " + habitNumber);
        }
    }

    public void displayHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits to display.");
        } else {
            System.out.println("Your habits:");
            for (Habit habit : habits.values()) {
                System.out.println("- " + habit.getNumber() + ". " + habit.getName());
            }
        }
    }

    public static void main(String[] args) {
        HabitTracker tracker = new HabitTracker();
        Scanner scanner = new Scanner(System.in);

        while (!loggedIn) {
            System.out.println("Welcome to the Habit Tracker!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                currentUser = User.login();
                loggedIn = currentUser != null;
            } else if (choice == 2) {
                currentUser = User.register();
                loggedIn = currentUser != null;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        tracker.loadHabitsFromFile(currentUser.getUniqueId());
        String choice;

        do {
            System.out.println("\nWelcome, " + User.getUserName() + "!" + "\n" + "uniqueId: " + User.getUniqueId());

            System.out.println("\nHabit Tracker Menu:");
            System.out.println("1. Add a habit");
            System.out.println("2. Remove a habit");
            System.out.println("3. Log a habit for today");
            System.out.println("4. Display habits");
            System.out.println("5. Display logs for a habit");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter the habit to add: ");
                    String habitName = scanner.nextLine();
                    tracker.addHabit(new Habit(tracker.nextHabitNumber++, habitName));
                    break;
                case "2":
                    System.out.print("Enter the number of the habit to remove: ");
                    int habitNumberToRemove = Integer.parseInt(scanner.nextLine());
                    tracker.removeHabit(habitNumberToRemove);
                    break;
                case "3":
                    System.out.print("Enter the number of the habit to log: ");
                    int habitNumberToLog = Integer.parseInt(scanner.nextLine());
                    Habit habitToLog = tracker.habits.get(habitNumberToLog);
                    if (habitToLog != null) {
                        habitToLog.addLog();
                        System.out.println("Habit logged for today: " + habitToLog.getName());
                    } else {
                        System.out.println("Habit not found with number: " + habitNumberToLog);
                    }
                    break;
                case "4":
                    tracker.displayHabits();
                    break;
                case "5":
                    System.out.print("Enter the number of the habit to display logs: ");
                    int habitNumberForLogs = Integer.parseInt(scanner.nextLine());
                    Habit habitForLogs = tracker.habits.get(habitNumberForLogs);
                    if (habitForLogs != null) {
                        habitForLogs.displayLogs();
                    } else {
                        System.out.println("Habit not found with number: " + habitNumberForLogs);
                    }
                    break;
                case "6":
                    tracker.saveHabitsToFile(currentUser.getUniqueId());
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("6"));

        scanner.close();
    }

    private void loadHabitsFromFile(String filename) {
        String folderPath = "/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static";
        String filePath = folderPath + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int habitNumber = Integer.parseInt(parts[0]);
                String habitName = parts[1];
                habits.put(habitNumber, new Habit(habitNumber, habitName));
                if (habitNumber >= nextHabitNumber) {
                    nextHabitNumber = habitNumber + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading habits from file: " + e.getMessage());
        }
    }
    private void saveHabitsToFile(String filename) {
        String folderPath = "/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static";
        String filePath = folderPath + filename + ".json";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Gson gson = new Gson();
            gson.toJson(getHabitLogs(), writer);
        } catch (IOException e) {
            System.out.println("Error saving habits to file: " + e.getMessage());
        }
    }

    private Map<String, Integer> getHabitLogs() {
        Map<String, Integer> habitLogs = new HashMap<>();
        for (Habit habit : habits.values()) {
            habitLogs.put(habit.getName(), habit.getNumber());
        }
        return habitLogs;
    }
}
