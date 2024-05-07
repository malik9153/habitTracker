package com.example.demo;

import com.example.demo.Entity.User;
import com.example.demo.Entity.habitEntities.HabitsEntity;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.utilities.ScannerPrinter;
import com.example.demo.utilities.loadHabitsFromFile;
import com.example.demo.utilities.saveHabitsToFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

public class HabitTracker {
    private static boolean loggedIn = false;
    private final User currentUser;
    private final Scanner scanner;
    private final ScannerPrinter scannerPrinter;
    private final HabitsEntity habitsEntity;


    public HabitTracker() {
        this.scannerPrinter = new ScannerPrinter();
        this.habitsEntity = new HabitsEntity();
        this.currentUser = new User();
        this.scanner = new Scanner(System.in);

    }

    private void handleLoginOrRegistration() {
        int choice;
        do {
            scannerPrinter.SignIn();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        currentUser.login();
                        loggedIn = true;
                        loadHabitsFromFile.loadHabits(currentUser.getUniqueId(), habitsEntity.habits);
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
        } while (choice != 1 && choice != 2);
    }


    public void handleUserActions(HabitActions habitActions) throws GeneralSecurityException, IOException {
        String choice;

        do {
            scannerPrinter.displayMenu();
            choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        habitActions.addingHabit(habitsEntity);
                        break;
                    case "2":
                        habitActions.removingHabit(habitsEntity);
                        break;
                    case "3":
                        habitActions.loggingHabit(habitsEntity);
                        break;
                    case "4":
                        habitsEntity.displayHabits();
                        break;
                    case "5":
                        habitActions.displayingLogs(habitsEntity);
                        break;
                    case "6":
                        habitActions.setReminder();
                        break;
                    case "7":
                        saveHabitsToFile.saveHabits(currentUser.getUniqueId(), habitsEntity);
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
        HabitActions habitActions = new HabitActions(scanner);

        scannerPrinter.welcomeMessage();
        while (!loggedIn) {
            handleLoginOrRegistration();
        }
        handleUserActions(habitActions);
    }
}


