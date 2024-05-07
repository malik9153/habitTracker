package com.example.demo.Entity;

import com.example.demo.Abstract.UserEntity;
import com.example.demo.exception.InvalidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class User extends UserEntity {


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
    public Boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.isEmpty();
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6; // Example validation: Password must be at least 6 characters
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

    @Override
    public void register() {
        JSONParser parser = new JSONParser();
        Scanner scanner = new Scanner(System.in);
        while (!loggedIn) {
            try {
                System.out.print("Type /q to quit: ");

                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                if (quittable(username)) {
                    return;
                }
                if (!isValidUsername(username)) {
                    throw new InvalidInputException("Invalid username");
                }
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (quittable(password)) {
                    return;
                }
                if (!isValidPassword(password)) {
                    throw new InvalidInputException("Invalid password");
                }
                String hashedInputtedPassword = hashPassword(password, salt);
                String uniqueId = UUID.randomUUID().toString();
                loggedIn = true;
                try {
                    Object obj = parser.parse(new FileReader("/Users/maliek.borwin/Library/CloudStorage/OneDrive-AutoTraderGroupPlc/Desktop/workspace/Digital Artefact/src/main/resources/static/UserAccount.json"));

                    JSONObject jsonObject = (JSONObject) obj;

                    JSONArray userArrayJson = (JSONArray) jsonObject.get("Users");

                    JSONObject newUser = new JSONObject();
                    newUser.put("Username", username);
                    newUser.put("Password", hashedInputtedPassword);
                    newUser.put("UniqueId", uniqueId);

                    setUserName(username);
                    setPassword(hashedInputtedPassword);
                    setUniqueId(uniqueId);

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
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void login() {
        Scanner scanner = new Scanner(System.in);
        while (!loggedIn) {
            try {
                System.out.print("Type /q to quit: ");

                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                if (quittable(username)) {
                    return;
                }

                if (!isValidUsername(username)) {
                    throw new InvalidInputException("Invalid username");
                }

                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (quittable(password)) {
                    return;
                }
                String hashedInputtedPassword = hashPassword(password, salt);

                if (checkUserExists(username)) {
                    if (checkPassword(hashedInputtedPassword)) {
                        System.out.println("Validated login successfully");
                        setUserName(username);
                        setPassword(hashedInputtedPassword);
                        setUniqueId(uniqueId);
                        return;
                    } else {
                        System.out.println("Invalid password");
                    }
                } else {
                    System.out.println("Invalid username");
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
            loggedIn = true;
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
