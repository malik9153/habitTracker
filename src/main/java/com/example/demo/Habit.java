package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Habit {
    private int LogNumber;
    private final String name;
    private ArrayList<LocalDate> logDates;

    public Habit(int logNumber, String name) {
        this.LogNumber = logNumber;
        this.name = name;
        this.logDates = new ArrayList<>();
    }

    public int getLogNumber() {
        return LogNumber;
    }

    public String getName() {
        return name;
    }
    public ArrayList<java.time.LocalDate> getLogDates() {
        return logDates;
    }


    public void addLog() {
        logDates.add(LocalDate.now());
        LogNumber++;
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