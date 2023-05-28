package org.example.controllers;

import org.example.data_access.UserDAO;
import org.example.models.User;

import java.sql.SQLException;
import java.util.Date;

public class UserController {
    private final UserDAO userDAO;
    public UserController() throws SQLException {
        userDAO = new UserDAO();
    }

    public void createUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.saveUser(user);
    }
}