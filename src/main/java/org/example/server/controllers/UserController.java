package org.example.server.controllers;

import org.example.server.data_access.UserDAO;
import org.example.server.models.User;

import java.sql.SQLException;
import java.util.Date;

public class UserController {
    private final UserDAO userDAO;
    public UserController() throws SQLException {
        userDAO = new UserDAO();
    }

    public void createUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday, new Date(), new Date());
        userDAO.saveUser(user);
    }

    public void deleteUser(String id) throws SQLException {
        User user = new User();
        user.setId(id);
        userDAO.deleteUser(user);
    }

    public void updateUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.updateUser(user);
    }

    public User getUser(String id) throws SQLException {
        return userDAO.getUser(id);
    }

}