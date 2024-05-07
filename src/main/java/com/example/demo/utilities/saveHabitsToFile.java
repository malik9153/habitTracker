package com.example.demo.utilities;

import com.example.demo.Entity.habitEntities.HabitsEntity;
import com.example.demo.Entity.habitEntities.HabitEntity;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class saveHabitsToFile {
    public static void saveHabits(String filename, HabitsEntity habitsEntity)  {

        String folderPath = "src/main/resources/static/";
        String filePath = folderPath + filename + ".json";
        JSONObject habitObj = new JSONObject();

        for (Object key : habitsEntity.habits.keySet()) {
            JSONObject logObj = new JSONObject();
            String habitName = key.toString();
            HabitEntity habitEntityDetails =  habitsEntity.habits.get(habitName);
            ArrayList<LocalDate> logDates = habitEntityDetails.getLogDates();
            Integer logNumber =  habitEntityDetails.getLogNumber();
            logObj.put(logNumber, logDates);
            habitObj.put(habitName, logObj);
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(habitObj.toJSONString());
            System.out.println("Habits saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving habits to file: " + e.getMessage());
        }
    }
}


