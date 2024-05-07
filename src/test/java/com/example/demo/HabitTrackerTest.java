package com.example.demo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HabitTrackerTest {

    @Test
    public void addHabit_ValidInput_Success() {
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.addHabit("Exercise", habitTracker);
        assertTrue(habitTracker.habits.containsKey("Exercise"));
    }

    @Test
    public void removeHabit_HabitExists_Success() {
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.addHabit("Exercise", habitTracker);
        habitTracker.removeHabit("Exercise");
        assertFalse(habitTracker.habits.containsKey("Exercise"));
    }

    @Test
    public void displayHabits_EmptyHabitTracker_DisplayNoHabitsMessage() {
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.displayHabits(habitTracker);
    }

}
