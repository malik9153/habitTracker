package com.example.demo;

import com.example.demo.Entity.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void register_ValidInput_Success() {
        String input = "testUsername\ntestPassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        User user = new User();
        user.register();

        assertNotNull(user.getUserName());
        assertNotNull(user.getUniqueId());
    }

    @Test
    void login_ValidInput_Success() {
        String input = "testUsername\ntestPassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        User user = new User();
        user.register();
        user.login();

        assertTrue(user.isLoggedIn());
    }


}
