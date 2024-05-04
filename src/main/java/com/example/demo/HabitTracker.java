package com.example.demo;

import com.example.demo.utilities.loadHabitsFromFile;
import com.example.demo.utilities.saveHabitsToFile;

import java.util.HashMap;
import java.util.Scanner;

public class HabitTracker {
    public HashMap<String, Habit> habits;
    private int nextHabitNumber;
    private static boolean loggedIn = false;
    public User currentUser = new User();


    public HabitTracker() {
        habits = new HashMap<>();
        nextHabitNumber = 1;
    }
    public void addHabit(Habit habit) {
        habits.put(habit.getName(), habit);
        System.out.println("Habit added: " + habit.getName());
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
                System.out.println("- " + habit.getNumber() + ". " + habit.getName());
            }
        }
    }

    public void tracking() {
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
                currentUser.login();
                loggedIn = currentUser != null;
                tracker.habits = loadHabitsFromFile.loadHabits(currentUser.getUniqueId(), habits);
            } else if (choice == 2) {
                currentUser.register();
                loggedIn = currentUser != null;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }


        }
        String choice;

        do {
            System.out.println("\nWelcome, " + currentUser.getUserName() + "!" + "\n" + "uniqueId: " + currentUser.getUniqueId());

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
                    System.out.print("Enter the name of the habit to remove: ");
                    String habitNameToRemove = scanner.nextLine();
                    tracker.removeHabit(habitNameToRemove);
                    break;
                case "3":
                    System.out.print("Enter the name of the habit to log: ");
                    String habitNameToLog = scanner.nextLine();
                    Habit habitToLog = tracker.habits.get(habitNameToLog);
                    if (habitToLog != null) {
                        habitToLog.addLog();
                        System.out.println("Habit logged for today: " + habitToLog.getName());
                    } else {
                        System.out.println("Habit not found with number: " + habitToLog);
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
                    saveHabitsToFile.saveHabits(currentUser.getUniqueId(),tracker);
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("6"));

        scanner.close();
    }
}

