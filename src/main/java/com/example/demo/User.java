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
    static String userName;
    static String uniqueId;
    static String password;

    static boolean loggedIn = false;


    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUniqueId() {
        return uniqueId;
    }

    public static void setUniqueId(String uniqueId) {
        User.uniqueId = uniqueId;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static User register() {
        JSONParser parser = new JSONParser();
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.print("Type /q to quit: ");

            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            if (quittable(userInput)) {
                return null;
            }
            String username = userInput;

            System.out.print("Enter your password: ");
            userInput = scanner.nextLine();

            if (quittable(userInput)) {
                return null;
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
        return null;
    }


    public static User login() {
        Scanner scanner = new Scanner(System.in);
        while (!loggedIn) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            if (quittable(username)) {
                return null;
            }
            if (checkUserExists(username)) {
                System.out.print("Enter your password: ");
                String inputtedPassword = scanner.nextLine();
                if (quittable(inputtedPassword)) {
                    return null;
                }
                if (validatePassword(inputtedPassword)) {
                    System.out.print("Validated login successfully");
                    User user = new User();
                    setUserName(username);
                    setPassword(inputtedPassword);
                    setUniqueId(uniqueId);
                    return user;
                } else {
                    System.out.print("Invalid password \n");
                }
            } else {
                System.out.print("Invalid username \n");
            }
        }
        return null;
    }

    public static boolean checkUserExists(String username) {
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
    public static boolean validatePassword(String inputtedPassword){
        return Objects.equals(inputtedPassword, password);
    }
}
