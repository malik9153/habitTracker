package com.example.demo.Entity.habitEntities;

import com.example.demo.interfaces.Loggable;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabitEntity implements Loggable {
    private int logNumber;
    private final String name;
    private final ArrayList<LocalDate> logDates;

    public HabitEntity(String name) {
        this.logNumber = 0;
        this.name = name;
        this.logDates = new ArrayList<>();
    }

    public HabitEntity(String name, JSONObject habitDetails) {
        this.name = name;
        this.logDates = new ArrayList<>();

        String key = habitDetails.keySet().iterator().next().toString();
        this.logNumber = Integer.parseInt(key);
        ArrayList<?> dateValues = (ArrayList<?>) habitDetails.get(key);

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
    public int getLogNumber() {
        return logNumber;
    }

    public String getName() {
        return name;
    }

    public ArrayList<LocalDate> getLogDates() {
        return logDates;
    }

    @Override
    public void addLog() {
        logDates.add(LocalDate.now());
        logNumber++;
    }

    public void filterLogsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> filteredLogDates = logDates.stream()
                .filter(date -> date.isAfter(startDate) && date.isBefore(endDate))
                .collect(Collectors.toList());
        displayLogs(filteredLogDates );
    }
    public void displayLogs(List<LocalDate> filteredLogDates) {
        if (filteredLogDates.isEmpty()) {
            System.out.println("No logDates for habit: " + name);
        } else {
            System.out.println("Logs for habit: " + name);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            for (LocalDate log : filteredLogDates) {
                System.out.println("- Date: " + log.format(formatter));
            }
        }
    }

    @Override
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