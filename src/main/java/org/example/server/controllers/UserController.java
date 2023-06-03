package org.example.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.data_access.BioDAO;
import org.example.server.data_access.UserDAO;
import org.example.server.models.Bio;
import org.example.server.models.User;

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

    public void createUser(String id, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
        User user = new User(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        userDAO.saveUser(user);
    }

    public void createBio(String userId, String biography, String location, String website) throws SQLException {
        Bio bio = new Bio(userId, biography, location, website);
        if (userDAO.getUser(userId) == null) throw new SQLException("User does not exist");
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
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(userDAO.getUser(id));
        return response;
    }

    public String getBioByUserId(String userId) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(bioDAO.getBio(userId));
        return response;
    }

    public String getUsers() throws SQLException, JsonProcessingException {
        ArrayList<User> users = userDAO.getUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(users);
        return response;
    }


    public String getBios() throws SQLException, JsonProcessingException {
        ArrayList<Bio> bios = bioDAO.getBios();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(bios);
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