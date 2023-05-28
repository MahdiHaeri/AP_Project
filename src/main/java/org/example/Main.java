package org.example;

import org.example.controllers.UserController;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            UserController userController = new UserController();
            userController.createUser("2", "mobin", "zaki", "mobinzaki@gmail.com", "123456789", "123456", "Iran", new Date());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}