package com.example.demo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class User {
    private String userName;
    private String passwordHash;
    private final String salt = generateSalt();
    private String uniqueId;
    private String password;

    private boolean loggedIn = false;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
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

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage());
        }
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
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

            String hashedInputtedPassword = hashPassword(userInput, salt);
            String uniqueId = UUID.randomUUID().toString();
            quit = true;
            try {
                Object obj = parser.parse(new FileReader("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json"));

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray userArrayJson = (JSONArray) jsonObject.get("Users");

                JSONObject newUser = new JSONObject();
                newUser.put("Username", username);
                newUser.put("Password", hashedInputtedPassword);
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
                String hashedInputtedPassword = hashPassword(inputtedPassword, salt);

                if (checkPassword(hashedInputtedPassword)) {
                    System.out.print("Validated login successfully");
                    setUserName(inputtedUsername);
                    setPassword(hashedInputtedPassword);
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

    public boolean checkPassword(String inputtedPassword) {
        return inputtedPassword.equals(password);
    }
}
