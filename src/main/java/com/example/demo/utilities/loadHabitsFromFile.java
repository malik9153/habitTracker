package com.example.demo.utilities;

import com.example.demo.Habit;

import java.io.FileReader;
import java.io.IOException;

import com.google.api.client.json.Json;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
public class loadHabitsFromFile {
    public static HashMap<String, Habit> loadHabitsFromFile1(String filename, HashMap<String, Habit> habits) {
        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String habitName = key.toString();
                int habitNumber =  Integer.parseInt(jsonObject.get(habitName).toString());
                Habit habit = new Habit(habitNumber, habitName);
                habits.put(habitName, habit);

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }

    public static HashMap<Integer, Habit> loadHabitsFromFile2(String filename, HashMap<Integer, Habit> habits)
    {
        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray habitArray = (JSONArray) jsonObject.get("habits");
            for (Object o : habitArray) {
                JSONObject habitObject = (JSONObject) o;
                Integer number = Integer.parseInt(habitObject.get("number").toString());
                String name = (String) habitObject.get("name");
                Habit habit = new Habit(number, name);
                habits.put(number, habit);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }
}
