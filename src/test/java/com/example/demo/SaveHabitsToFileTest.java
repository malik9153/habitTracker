package com.example.demo;

import com.example.demo.Entity.habitEntities.HabitsEntity;
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
        HabitsEntity habitsEntity = new HabitsEntity();
        habitsEntity.addHabit(EXERCISE);
        habitsEntity.addHabit(READING);
        habitsEntity.addHabit(MEDITATION);

        String filename = "saveTestHabits";

        saveHabitsToFile.saveHabits(filename, habitsEntity);

        assertTrue(Files.exists(Path.of("src/main/resources/static/" + filename + ".json")));

    }

    @Test
    void saveHabits_EmptyData_Success() {
        HabitsEntity habitsEntity = new HabitsEntity();
        String filename = "testEmptyHabits";

        saveHabitsToFile.saveHabits(filename, habitsEntity);

        assertTrue(Files.exists(Path.of("src/main/resources/static/" + filename + ".json")));

    }

}
