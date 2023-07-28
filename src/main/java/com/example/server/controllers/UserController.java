package com.example.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.server.data_access.BioDAO;
import com.example.server.data_access.UserDAO;
import com.example.server.models.Bio;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserController {
    private final UserDAO userDAO;
    private final BioDAO bioDAO;
    public UserController() throws SQLException {
        userDAO = new UserDAO();
        bioDAO = new BioDAO();
    }

    public boolean checkUserExists(String username, String password) throws SQLException {
        return userDAO.getUser(username, password) != null;
    }

    public void createUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.saveUser(user);
    }

    public void createBio(String userId, String biography, String location, String website) throws SQLException {
        Bio bio = new Bio(userId, biography, location, website);
        if (userDAO.getUser(userId) == null) throw new SQLException("User does not exist");
        if (bioDAO.getBio(userId) != null) {
            bioDAO.updateBio(bio);
            return;
        }
        bioDAO.saveBio(bio);
    }

    public void deleteUser(String id) throws SQLException {
        User user = new User();
        user.setId(id);
        userDAO.deleteUser(user);
    }

    public void deleteBio(String userId) throws SQLException {
        bioDAO.deleteBio(userId);
    }

    public void deleteUsers() throws SQLException {
        userDAO.deleteUsers();
    }

    public void deleteBios() throws SQLException {
        bioDAO.deleteBios();
    }

    public void updateUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.updateUser(user);
    }

    public void updateBio(String userId, String biography, String location, String website) throws SQLException {
        Bio bio = new Bio(userId, biography, location, website);
        bioDAO.updateBio(bio);
    }

    public String getUserById(String id) throws SQLException, JsonProcessingException {
        User user = userDAO.getUser(id);
        if (user == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }

    public String getBioByUserId(String userId) throws SQLException, JsonProcessingException {
        Bio bio = bioDAO.getBio(userId);
        if (bio == null) return "{}";
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bio);
    }

    public String getUsers() throws SQLException, JsonProcessingException {
        ArrayList<User> users = userDAO.getUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(users);
    }

    public boolean isUserExists (String ID) {
        try {
            return (userDAO.getUser(ID) != null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBios() throws SQLException, JsonProcessingException {
        ArrayList<Bio> bios = bioDAO.getBios();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bios);
    }
  
    public String getUserByIdAndPass(String id, String pass) throws SQLException, JsonProcessingException {
        User user = userDAO.getUser(id, pass);
        if (user == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }


}