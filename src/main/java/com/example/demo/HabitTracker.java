package com.example.demo;

import com.example.demo.utilities.loadHabitsFromFile;
import com.example.demo.utilities.saveHabitsToFile;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                currentUser.login();
                loggedIn = currentUser != null;
                habitTracker.habits = loadHabitsFromFile.loadHabits(currentUser.getUniqueId(), habits);
                break;
            case 2:
                currentUser.register();
                loggedIn = currentUser != null;
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
        System.out.print("Enter the number of the habit to display logs: ");
        String habitNameForLogs = (scanner.nextLine());
        Habit habitForLogs = habitTracker.habits.get(habitNameForLogs);
        if (habitForLogs != null) {
            habitForLogs.displayLogs();
        } else {
            System.out.println("Habit not found : " + habitNameForLogs);
        }
    }

    private void setReminder() throws GeneralSecurityException, IOException {
        System.out.print("Enter the name of the habit to set a reminder for: ");
        String habitNameForReminder = scanner.nextLine();

        System.out.print("Enter the Date for the reminder in the format of yyyy-MM-dd: ");
        String dateForReminder = scanner.nextLine();

        System.out.print("Enter the time for the reminder in the format of HH:mm: ");
        String timeForReminder = scanner.nextLine();
        LocalTime timeForReminderParsed = LocalTime.parse(timeForReminder, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(15);

        DateTime startDateTimeForReminder = new DateTime(dateForReminder + "T" + timeForReminder + ":00-07:00");
        DateTime endDateTimeForReminder = new DateTime(dateForReminder + "T" + timeForReminderParsed.toString() + ":00-07:00");

        CalendarQuickstart.setEvent(habitNameForReminder, startDateTimeForReminder, endDateTimeForReminder);
    }

        public void handleUserActions (HabitTracker habitTracker) throws GeneralSecurityException, IOException {
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
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (!choice.equals("7"));

            scanner.close();
        }

        public void tracking () throws GeneralSecurityException, IOException {
            HabitTracker habitTracker = new HabitTracker();
            welcomeMessage();
            while (!loggedIn) {
                handleLoginOrRegistration(habitTracker);
            }
            handleUserActions(habitTracker);
        }
    }


