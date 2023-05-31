package org.example.server.HttpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.example.server.controllers.UserController;
import org.json.JSONObject;

public class UserHandler implements HttpHandler {
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
        String[] splitedPath = path.split("/");
        switch (method) {
            case "GET":
                if (splitedPath.length == 2) {
                    try {
                        response = userController.getUsers();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
                    String userId = splitedPath[splitedPath.length - 1];
                    try {
                        response = userController.getUserById(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "POST":
                // Read the request body
                InputStream requestBody = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
                requestBody.close();

                // Process the user creation based on the request body
                String newUser = body.toString();
                JSONObject jsonObject = new JSONObject(newUser);
                try {
                    userController.createUser(jsonObject.getString("id"), jsonObject.getString("firstName"), jsonObject.getString("lastName"), jsonObject.getString("email"), jsonObject.getString("phoneNumber"), jsonObject.getString("password"), jsonObject.getString("country"), new Date(jsonObject.getLong("birthday")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "this is done!";
                break;
            case "PUT":
                response = "This is the response users Put";
                break;
            case "DELETE":
                if (splitedPath.length == 2) {
                    try {
                        userController.deleteUsers();
                        response = "All users deleted";
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
                    String userId = splitedPath[splitedPath.length - 1];
                    try {
                        userController.deleteUser(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                response = "This is the response users Delete";
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