package com.example.demo;

import com.example.demo.Entity.habitEntities.HabitsEntity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HabitTrackerTest {

    @Test
    public void addHabit_ValidInput_Success() {
        HabitsEntity habitsEntity = new HabitsEntity();
        habitsEntity.addHabit("Exercise");
        assertTrue(habitsEntity.habits.containsKey("Exercise"));
    }

    @Test
    public void removeHabit_HabitExists_Success() {
        HabitsEntity habitsEntity = new HabitsEntity();
        habitsEntity.addHabit("Exercise");
        habitsEntity.removeHabit("Exercise");
        assertFalse(habitsEntity.habits.containsKey("Exercise"));
    }

    @Test
    public void displayHabits_EmptyHabitTracker_DisplayNoHabitsMessage() {
        HabitsEntity habitsEntity = new HabitsEntity();
        habitsEntity.displayHabits();
    }

}
