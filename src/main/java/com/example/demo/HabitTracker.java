package com.example.demo;

import com.example.demo.Entity.ColouredEvent;
import com.example.demo.Entity.ReminderEventEntity;
import com.example.demo.Exception.InvalidInputException;
import com.example.demo.utilities.loadHabitsFromFile;
import com.example.demo.utilities.saveHabitsToFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

public class HabitTracker {
    public HashMap<String, Habit> habits;
    private static boolean loggedIn = false;
    private final User currentUser;
    private final Scanner scanner;

    public HabitTracker() {
        habits = new HashMap<>();
        this.currentUser = new User();
        this.scanner = new Scanner(System.in);
    }

    public void addHabit(String habitName, HabitTracker habitTracker) {
        Habit newHabit = new Habit(habitName);
        habitTracker.habits.put(habitName, newHabit);
//        habits.put(habitName, newHabit);
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

    public void displayHabits(HabitTracker habitTracker) {
        if (habitTracker.habits.isEmpty()) {
            System.out.println("No habits to display.");
        } else {
            System.out.println("Your habits:");
            for (Habit habit : habitTracker.habits.values()) {
                System.out.println("- " + habit.getLogNumber() + ". " + habit.getName());
            }
        }
    }

    private void welcomeMessage() {
        System.out.println("Welcome to the Habit Tracker!");
    }

    private void handleLoginOrRegistration(HabitTracker habitTracker) {
        int choice;

        do {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        currentUser.login();
                        loggedIn = true;
                        habitTracker.habits = loadHabitsFromFile.loadHabits(currentUser.getUniqueId(), habits);
                        break;
                    case 2:
                        currentUser.register();
                        loggedIn = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 1 && choice != 2); // Continue loop until a valid choice is made
    }


    private void displayMenu() {
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

    private void addingHabit(HabitTracker habitTracker) {
        System.out.print("Enter the habit to add: ");
        String habitName = scanner.nextLine();
        addHabit(habitName, habitTracker);
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
        System.out.print("Enter the name of the habit to display logs: ");
        String habitNameForLogs = scanner.nextLine();
        Habit habitForLogs = habitTracker.habits.get(habitNameForLogs);
        System.out.println("Do you want all the logs or custom date range  y/c: ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            if (habitForLogs != null) {
                habitForLogs.displayLogs();
            } else {
                System.out.println("Habit not found : " + habitNameForLogs);
            }
        } else if (choice.equalsIgnoreCase("c")) {
            if (habitForLogs != null) {
                System.out.print("Enter start date (yyyy-MM-dd): ");
                LocalDate startDate = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter end date (yyyy-MM-dd): ");
                LocalDate endDate = LocalDate.parse(scanner.nextLine());
                habitForLogs.filterLogsByDateRange(startDate, endDate);
            } else {
                System.out.println("Habit not found : " + habitNameForLogs);
            }
        }
    }

    private void setReminder() throws GeneralSecurityException, IOException, InvalidInputException {
        System.out.print("Do you want to colour the reminder? (y/n): ");
        String colourConfirmation = scanner.nextLine();

        System.out.print("Enter the name of the habit to set a reminder for: ");
        String habitNameForReminder = scanner.nextLine();

        System.out.print("Enter the Date for the reminder in the format of yyyy-MM-dd: ");
        String dateForReminder = scanner.nextLine();

        System.out.print("Enter the time for the reminder in the format of HH:mm: ");
        String timeForReminder = scanner.nextLine();

        if (!colourConfirmation.equals("y") && !colourConfirmation.equals("n")) {
            throw new InvalidInputException("Invalid input for colour confirmation. Please enter 'y' or 'n'.");
        }

        try {
            LocalDate.parse(dateForReminder, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Please enter date in the format yyyy-MM-dd.", e);
        }

        try {
            LocalTime.parse(timeForReminder, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid time format. Please enter time in the format HH:mm.", e);
        }

        if (colourConfirmation.equals("y")) {
            ColouredEvent colouredEvent = new ColouredEvent(habitNameForReminder, "Manchester", "", dateForReminder, timeForReminder);
            colouredEvent.createEvent();
            colouredEvent.createAndSendReminder();
        } else {
            ReminderEventEntity reminderEvent = new ReminderEventEntity(habitNameForReminder, "Manchester", "", dateForReminder, timeForReminder);
            reminderEvent.createEvent();
            reminderEvent.createAndSendReminder();
        }
    }

    public void handleUserActions(HabitTracker habitTracker) throws GeneralSecurityException, IOException {
        String choice;

        do {
            displayMenu();
            choice = scanner.nextLine();
            try {
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
                        habitTracker.displayHabits(habitTracker);
                        break;
                    case "5":
                        displayingLogs(habitTracker);
                        break;
                    case "6":
                        setReminder();
                        break;
                    case "7":
                        saveHabitsToFile.saveHabits(currentUser.getUniqueId(), habitTracker);
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InvalidInputException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        } while (!choice.equals("7"));

        scanner.close();
    }

    public void tracking() throws GeneralSecurityException, IOException {
        HabitTracker habitTracker = new HabitTracker();
        welcomeMessage();
        while (!loggedIn) {
            handleLoginOrRegistration(habitTracker);
        }
        handleUserActions(habitTracker);
    }
}


