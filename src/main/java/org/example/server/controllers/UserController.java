package org.example.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.data_access.UserDAO;
import org.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public void deleteUser(String id) throws SQLException {
        User user = new User();
        user.setId(id);
        userDAO.deleteUser(user);
    }

    public void deleteUsers() throws SQLException {
        userDAO.deleteUsers();
    }

    public void updateUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.updateUser(user);
    }

    public String getUserById(String id) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(userDAO.getUser(id));
        return response;
    }

    public String getUsers() throws SQLException, JsonProcessingException {
        ArrayList<User> users = userDAO.getUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(users);
        return response;
    }

    public String getUserByIdAndPass(String id, String pass) throws SQLException, JsonProcessingException {
        User user = userDAO.getUser(id, pass);
        if (user == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(user);
        return response;
    }
}