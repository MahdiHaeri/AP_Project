package com.example.server.HttpHandlers;

import com.example.server.controllers.UserController;
import com.example.server.utils.JWTController;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class LoginHandler {

    private final UserController userController;

    public LoginHandler() throws SQLException {
        userController = new UserController();
    }
    public Object handlePostLogin(Request request, Response response) throws SQLException {
        JSONObject jsonObject = new JSONObject(request.body());
        String userId = jsonObject.getString("userId");
        String password = jsonObject.getString("password");
        if (!userController.checkUserExists(userId, password)) {
            response.status(401);
            return "Invalid credentials";
        }

        // Create a JWT token
        String token = JWTController.generateJwtToken(userId);

        // Now, include the token in the response
        response.status(200);
        response.type("application/json");
        response.body(token);

        return response.body();
    }
}
