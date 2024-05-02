package com.example.demo.utilities;

import com.example.demo.Habit;
import com.example.demo.HabitTracker;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class saveHabitsToFile {
    public static void saveHabits(String filename, HabitTracker tracker) {

        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONObject habitObj = new JSONObject();

        for (Object key : tracker.habits.keySet()) {
            String habitName = key.toString();
            Habit habitDetails =  tracker.habits.get(habitName);
            Integer logNumber =  habitDetails.getNumber();
            habitObj.put(habitName, logNumber);
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(habitObj.toJSONString());
            System.out.println("Habits saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving habits to file: " + e.getMessage());
        }
    }
}


