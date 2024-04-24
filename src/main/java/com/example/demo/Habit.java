package com.example.demo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Habit {
    private int number;
    private String name;
    private ArrayList<LogHabit> logs;

    public Habit(int number, String name) {
        this.number = number;
        this.name = name;
        this.logs = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void addLog() {
        logs.add(new LogHabit(name));
    }

    public void displayLogs() {
        if (logs.isEmpty()) {
            System.out.println("No logs for habit: " + name);
        } else {
            System.out.println("Logs for habit: " + name);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            for (LogHabit log : logs) {
                System.out.println("- Date: " + log.getDate().format(formatter));
            }
        }
    }
}