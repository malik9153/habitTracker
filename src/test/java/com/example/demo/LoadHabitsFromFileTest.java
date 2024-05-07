package com.example.demo;

import com.example.demo.Entity.habitEntities.HabitEntity;
import com.example.demo.utilities.loadHabitsFromFile;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class LoadHabitsFromFileTest {

    @Test
    void loadHabits_ValidFile_Success() {
        String filename = "testHabits";
        HashMap<String, HabitEntity> habits = new HashMap<>();

        habits = loadHabitsFromFile.loadHabits(filename, habits);

        assertFalse(habits.isEmpty());

        assertTrue(habits.containsKey("Exercise"));
        assertTrue(habits.containsKey("Reading"));
        assertTrue(habits.containsKey("Meditation"));

        HabitEntity exerciseHabitEntity = habits.get("Exercise");
        assertNotNull(exerciseHabitEntity);
        assertEquals(3, exerciseHabitEntity.getLogNumber());
    }

    @Test
    void loadHabits_InvalidFile_ExceptionThrown() {
        String filename = "nonExistentFile";
        HashMap<String, HabitEntity> habits = new HashMap<>();

        assertThrows(RuntimeException.class, () -> loadHabitsFromFile.loadHabits(filename, habits));
    }


}
