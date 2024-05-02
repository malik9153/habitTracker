package com.example.demo.utilities;

import com.example.demo.Habit;
import com.example.demo.HabitTracker;
import com.google.api.client.json.Json;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class saveHabitsToFile {
    public static void saveHabitsToFile1(String filename, HabitTracker tracker) {
        JSONObject jsonObject = new JSONObject();
        JSONArray habitsArray = new JSONArray();

        for (Map.Entry<String, Habit> entry : tracker.habits.entrySet()) {
            JSONObject habitObj = new JSONObject();
            System.out.println(entry.getKey());
            habitObj.put("number", entry.getKey());
            habitObj.put("name", entry.getValue().getName());
            habitsArray.add(habitObj);
        }

        jsonObject.put("habits", habitsArray);

        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            System.out.println("Habits saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving habits to file: " + e.getMessage());
        }
    }
    public static void saveHabitsToFile2(String filename, HabitTracker tracker) {
        JSONObject jsonObject;

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


