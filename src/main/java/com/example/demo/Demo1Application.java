package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        SpringApplication.run(Demo1Application.class, args);
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.tracking();
    }

}
