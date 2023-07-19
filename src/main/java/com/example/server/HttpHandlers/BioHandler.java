package com.example.server.HttpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.SQLException;

import com.example.server.controllers.UserController;
import org.json.JSONObject;

public class BioHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UserController userController = null;
        try {
            userController = new UserController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitPath = path.split("/");

        // Read the request body
        InputStream requestBody = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        requestBody.close();

        switch (method) {
            case "GET":
                if (splitPath.length == 2) {
                    try {
                        response = userController.getBios();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
                    String userId = splitPath[splitPath.length - 1];
                    try {
                        response = userController.getBioByUserId(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "POST":
                // Process the bio creation based on the request body
                String newUser = body.toString();
                JSONObject jsonObject = new JSONObject(newUser);
                try {
                    userController.createBio(jsonObject.getString("userId"), jsonObject.getString("biography"), jsonObject.getString("location"), jsonObject.getString("website"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "this is done!";
                break;
            case "PUT":
                // Process the bio update based on the request body
                String updatedUser = body.toString();
                JSONObject updatedJsonObject = new JSONObject(updatedUser);
                try {
                    userController.updateBio(updatedJsonObject.getString("userId"), updatedJsonObject.getString("biography"), updatedJsonObject.getString("location"), updatedJsonObject.getString("website"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "This is the response bios Put";
                break;
            case "DELETE":
                if (splitPath.length == 2) {
                    try {
                        userController.deleteBios();
                        response = "All bios deleted";
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
                    String userId = splitPath[splitPath.length - 1];
                    try {
                        userController.deleteBio(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                response = "This is the response bios Delete";
                break;
            default:
                break;
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}