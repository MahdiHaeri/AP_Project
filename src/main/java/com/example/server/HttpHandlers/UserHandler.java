package com.example.server.HttpHandlers;


import com.example.server.controllers.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Date;

public class UserHandler {

    private final UserController userController;

    public UserHandler() throws SQLException {
        userController = new UserController();
    }

    public Object handleGetUser(Request request, Response response) {
        try {
            return userController.getUsers();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetUserById(Request request, Response response) {
        String username = request.params(":username");
        try {
            response.body(userController.getUserById(username));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handlePostUser(Request request, Response response) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request.body());

        String id = jsonObject.getString("id");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phoneNumber");
        String password = jsonObject.getString("password");
        String country = jsonObject.getString("country");
        Date birthday = new Date(jsonObject.getLong("birthday"));

        try {
            userController.createUser(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("User created successfully");
        return response.body();
    }

    public Object handlePutUser(Request request, Response response) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request.body());

        String id = jsonObject.getString("id");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phoneNumber");
        String password = jsonObject.getString("password");
        String country = jsonObject.getString("country");
        Date birthday = new Date(jsonObject.getLong("birthday"));

        try {
            userController.updateUser(id, firstName, lastName, email, phoneNumber, password, country, birthday);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("User updated successfully");
        return response.body();
    }

    public Object handleDeleteUserById(Request request, Response response) throws JsonProcessingException {
        String id = request.params(":username");

        try {
            userController.deleteUser(id);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("User deleted successfully");
        return response.body();
    }

    public Object handleDeleteUser(Request request, Response response) throws JsonProcessingException {
        try {
            userController.deleteUsers();
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Users deleted successfully");
        return response.body();
    }





    public Object handleGetBio(Request request, Response response) {
        try {
            response.body(userController.getBios());
            return response.body();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetBioById(Request request, Response response) {
        String username = request.params(":username");
        try {
            response.body(userController.getBioByUserId(username));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handlePostBio(Request request, Response response) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request.body());
        String userId = request.params(":username");
        String biography = jsonObject.getString("biography");
        String location = jsonObject.getString("location");
        String website = jsonObject.getString("website");

        try {
            userController.createBio(userId, biography, location, website);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Bio created successfully");
        return response.body();
    }

    public Object handlePutBio(Request request, Response response) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request.body());
        String userId = request.params(":username");
        String biography = jsonObject.getString("biography");
        String location = jsonObject.getString("location");
        String website = jsonObject.getString("website");

        try {
            userController.updateBio(userId, biography, location, website);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Bio updated successfully");
        return response.body();
    }

    public Object handleDeleteBioById(Request request, Response response) throws JsonProcessingException {
        String username = request.params(":username");
        try {
            userController.deleteBio(username);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Bio deleted successfully");
        return response.body();
    }

    public Object handleDeleteBio(Request request, Response response) throws JsonProcessingException {
        try {
            userController.deleteBios();
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Bios deleted successfully");
        return response.body();
    }
}