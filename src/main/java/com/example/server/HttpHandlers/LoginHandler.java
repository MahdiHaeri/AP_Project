package com.example.server.HttpHandlers;

import com.example.server.utils.JWTController;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public Object handlePostLogin(Request request, Response response) {
        // Your authentication logic here...
        // Assuming the user has been authenticated and the token should be generated

        // For demonstration purposes, let's assume you have a user ID and secret key.

        JSONObject jsonObject = new JSONObject(request.body());
        // Create a JWT token
        String token = JWTController.generateJwtToken(jsonObject.getString("userId"));

        // Now, include the token in the response
        response.status(200);
        response.type("application/json");
        response.body(token);

        return response.body();
    }
}
