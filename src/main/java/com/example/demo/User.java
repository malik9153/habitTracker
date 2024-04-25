package com.example.demo;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class User {
    private String userName;
    private String uniqueId;
    private String password;

    private final boolean loggedIn = false;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public  String getUserName() {
        return this.userName;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void register() {
        JSONParser parser = new JSONParser();
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.print("Type /q to quit: ");

            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            if (quittable(userInput)) {
                return;
            }
            String username = userInput;

            System.out.print("Enter your password: ");
            userInput = scanner.nextLine();

            if (quittable(userInput)) {
                return;
            }
            String password = userInput;

            String uniqueId = UUID.randomUUID().toString();


            quit = true;
            try {
                Object obj = parser.parse(new FileReader("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json"));

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray userArrayJson = (JSONArray) jsonObject.get("Users");

                JSONObject newUser = new JSONObject();
                newUser.put("Username", username);
                newUser.put("Password", password);
                newUser.put("UniqueId", uniqueId);


                userArrayJson.add(newUser);

                jsonObject.put("Users", userArrayJson);

                FileWriter fileWriter = new FileWriter("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json");
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                System.out.println("User registered successfully.");
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void login() {
        Scanner scanner = new Scanner(System.in);
        while (!loggedIn) {
            System.out.print("Enter your username: ");
            String inputtedUsername = scanner.nextLine();
            if (quittable(inputtedUsername)) {
                return;
            }
            if (checkUserExists(inputtedUsername)) {
                System.out.print("Enter your password: ");
                String inputtedPassword = scanner.nextLine();
                if (quittable(inputtedPassword)) {
                    return;
                }
                if (validatePassword(inputtedPassword)) {
                    System.out.print("Validated login successfully");
                    setUserName(inputtedUsername);
                    setPassword(inputtedPassword);
                    setUniqueId(uniqueId);
                    return;
                } else {
                    System.out.print("Invalid password \n");
                }
            } else {
                System.out.print("Invalid username \n");
            }
        }
    }

    public boolean checkUserExists(String username) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray userArrayJson = (JSONArray) jsonObject.get("Users");

            for (int i = 0; i < userArrayJson.size(); i++) {
                JSONObject userJson = (JSONObject) userArrayJson.get(i);
                String name = (String) userJson.get("Username");
                if (name.equals(username)) {
                    password = userJson.get("Password").toString();
                    uniqueId = userJson.get("UniqueId").toString();
                    return true;
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean quittable(String input) {
        return Objects.equals(input, "/q");
    }

//    public static void accessHabitFile(String uniqueId) {
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json"));
//        }
//    }
    public boolean validatePassword(String inputtedPassword){
        return Objects.equals(inputtedPassword, password);
    }
}
