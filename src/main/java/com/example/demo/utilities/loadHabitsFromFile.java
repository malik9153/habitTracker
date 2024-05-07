package com.example.demo.utilities;

import com.example.demo.Entity.habitEntities.HabitEntity;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
public class loadHabitsFromFile {
    public static HashMap<String, HabitEntity> loadHabits(String filename, HashMap<String, HabitEntity> habits) {
        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String habitName = key.toString();
                Object habitDetails = jsonObject.get(habitName);
                JSONObject habitDetailsObject = (JSONObject) habitDetails;
                HabitEntity habitEntity = new HabitEntity(habitName, habitDetailsObject);
                habits.put(habitName, habitEntity);

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }
}
