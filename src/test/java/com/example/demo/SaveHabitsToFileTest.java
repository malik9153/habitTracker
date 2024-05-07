package com.example.demo;

import com.example.demo.utilities.saveHabitsToFile;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveHabitsToFileTest {

    public static final String EXERCISE = "Exercise";
    public static final String READING = "Reading";
    public static final String MEDITATION = "Meditation";

    @Test
    void saveHabits_ValidData_Success() {
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.addHabit(EXERCISE, habitTracker);
        habitTracker.addHabit(READING, habitTracker);
        habitTracker.addHabit(MEDITATION, habitTracker);

        String filename = "saveTestHabits";

        saveHabitsToFile.saveHabits(filename, habitTracker);

        assertTrue(Files.exists(Path.of("src/main/resources/static/" + filename + ".json")));

    }

    @Test
    void saveHabits_EmptyData_Success() {
        HabitTracker habitTracker = new HabitTracker();
        String filename = "testEmptyHabits";

        saveHabitsToFile.saveHabits(filename, habitTracker);

        assertTrue(Files.exists(Path.of("src/main/resources/static/" + filename + ".json")));

    }

}
