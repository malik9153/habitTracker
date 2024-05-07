package com.example.demo.Entity.habitEntities;

import java.util.HashMap;

public class HabitsEntity{

    public HashMap<String, HabitEntity> habits = new HashMap<>();

    public HashMap<String, HabitEntity> getHabits() {
       return this.habits;
    }
    public void setHabits(HashMap<String, HabitEntity> habits) {
        this.habits = habits;
    }

    public HabitEntity getHabit(String habitName) {
        return habits.get(habitName);
    }


    public void addHabit(String habitName) {
        HabitEntity newHabitEntity = new HabitEntity(habitName);
        habits.put(habitName, newHabitEntity);
        System.out.println("Habit added: " + habitName);
    }

    public void removeHabit(String habitName) {
        HabitEntity habitEntityToRemove = habits.remove(habitName);
        if (habitEntityToRemove != null) {
            System.out.println("Habit removed: " + habitEntityToRemove.getName());
        } else {
            System.out.println("Habit not found: " + habitName);
        }
    }
    public void displayHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits to display.");
        } else {
            System.out.println("Your habits:");
            for (HabitEntity habitEntity : habits.values()) {
                System.out.println("- " + habitEntity.getLogNumber() + ". " + habitEntity.getName());
            }
        }
    }
}
