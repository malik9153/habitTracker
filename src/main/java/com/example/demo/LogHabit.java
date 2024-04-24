package com.example.demo;

import java.time.LocalDate;

class LogHabit {
    private final LocalDate date;
    private final String habitName;

    public LogHabit(String habitName) {
        this.date = LocalDate.now();
        this.habitName = habitName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getHabitName() {
        return habitName;
    }
}