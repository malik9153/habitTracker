package com.example.demo.utilities;

import com.example.demo.Habit;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
public class loadHabitsFromFile {
    public static HashMap<String, Habit> loadHabits(String filename, HashMap<String, Habit> habits) {
        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String habitName = key.toString();
                JSONObject habitDetails = jsonObject.get(habitName).toString();
                Habit habit = new Habit(habitNumber, habitName);
                habits.put(habitName, habit);

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }
}
