package com.example.demo;

import com.example.demo.Entity.eventEntities.ColouredEventEntity;
import com.example.demo.Entity.eventEntities.ReminderEventEntity;
import com.example.demo.Entity.habitEntities.HabitEntity;
import com.example.demo.Entity.habitEntities.HabitsEntity;
import com.example.demo.exception.InvalidInputException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HabitActions {

    Scanner scanner;

    public HabitActions(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addingHabit(HabitsEntity habitsEntity) {
        System.out.print("Enter the habit to add: ");
        String habitName = scanner.nextLine();
        habitsEntity.addHabit(habitName);
    }
    public void removingHabit(HabitsEntity habitsEntity) {
        System.out.print("Enter the name of the habit to remove: ");
        String habitNameToRemove = scanner.nextLine();
        habitsEntity.removeHabit(habitNameToRemove);
    }
    public void loggingHabit(HabitsEntity habitsEntity) {
        System.out.print("Enter the name of the habit to log: ");
        String habitNameToLog = scanner.nextLine();
        HabitEntity habitEntityToLog = habitsEntity.getHabit(habitNameToLog);
        if (habitEntityToLog != null) {
            habitEntityToLog.addLog();
            System.out.println("Habit logged for today: " + habitEntityToLog.getName());
        } else {
            System.out.println("Habit not found with number: " + habitEntityToLog);
        }
    }

    public void displayingLogs(HabitsEntity habitsEntity) {
        System.out.print("Enter the name of the habit to display logs: ");
        String habitNameForLogs = scanner.nextLine();
        HabitEntity habitEntityForLogs = habitsEntity.habits.get(habitNameForLogs);
        System.out.println("Do you want all the logs or custom date range  y/c: ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            if (habitEntityForLogs != null) {
                habitEntityForLogs.displayLogs();
            } else {
                System.out.println("Habit not found : " + habitNameForLogs);
            }
        } else if (choice.equalsIgnoreCase("c")) {
            if (habitEntityForLogs != null) {
                System.out.print("Enter start date (yyyy-MM-dd): ");
                LocalDate startDate = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter end date (yyyy-MM-dd): ");
                LocalDate endDate = LocalDate.parse(scanner.nextLine());
                habitEntityForLogs.filterLogsByDateRange(startDate, endDate);
            } else {
                System.out.println("Habit not found : " + habitNameForLogs);
            }
        }
    }

    public void setReminder() throws GeneralSecurityException, IOException, InvalidInputException {
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
            ColouredEventEntity colouredEventEntity = new ColouredEventEntity(habitNameForReminder, "Manchester", "", dateForReminder, timeForReminder);
            colouredEventEntity.createEvent();
            colouredEventEntity.createAndSendReminder();
        } else {
            ReminderEventEntity reminderEvent = new ReminderEventEntity(habitNameForReminder, "Manchester", "", dateForReminder, timeForReminder);
            reminderEvent.createEvent();
            reminderEvent.createAndSendReminder();
        }
    }
}
