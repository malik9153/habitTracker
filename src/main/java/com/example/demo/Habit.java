package com.example.demo;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Habit {
    private int logNumber;
    private final String name;
    private final ArrayList<LocalDate> logDates;

    public Habit(String name) {
        this.logNumber = 0;
        this.name = name;
        this.logDates = new ArrayList<>();
    }

    public Habit(String name, JSONObject habitDetails) {
        this.name = name;
        this.logDates = new ArrayList<>();

        // Extracting log number and dates from the habitDetails JSON object
        String key = habitDetails.keySet().iterator().next().toString(); // Assuming only one key
        this.logNumber = Integer.parseInt(key);
        ArrayList<?> dateValues = (ArrayList<?>) habitDetails.get(key);

        // Parsing and adding dates to logDates list
        for (Object dateValue : dateValues) {
            if (dateValue instanceof ArrayList) {
                ArrayList<?> dateComponents = (ArrayList<?>) dateValue;
                LocalDate date = parseLocalDate(dateComponents);
                if (date != null) {
                    logDates.add(date);
                }
            }
        }
    }

    // Helper method to parse LocalDate from date components
    private LocalDate parseLocalDate(ArrayList<?> dateComponents) {
        if (dateComponents.size() >= 3) {
            int year = (int) dateComponents.get(0);
            int month = (int) dateComponents.get(1);
            int day = (int) dateComponents.get(2);
            try {
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Getters for logNumber, name, and logDates
    public int getLogNumber() {
        return logNumber;
    }

    public String getName() {
        return name;
    }

    public ArrayList<LocalDate> getLogDates() {
        return logDates;
    }


    public void addLog() {
        logDates.add(LocalDate.now());
        logNumber++;
    }


    public void displayLogs() {
        if (logDates.isEmpty()) {
            System.out.println("No logDates for habit: " + name);
        } else {
            System.out.println("Logs for habit: " + name);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            for (LocalDate log : logDates) {
                System.out.println("- Date: " + log.format(formatter));
            }
        }
    }
}