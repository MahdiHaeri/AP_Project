package com.example.server.HttpHandlers;

import com.example.server.utils.JWTController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import com.example.server.controllers.UserController;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if (pathParts.length == 4 && "login".equals(pathParts[1])) {
                String username = pathParts[2];
                String password = pathParts[3];

                // Authenticate user (you can add your authentication logic here)
                boolean userExists = false;
                try {
                    userExists = checkUserExists(username, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (!userExists) {
                    // User doesn't exist, return an error response
                    exchange.sendResponseHeaders(401, -1);
                    return;
                }

                // Generate the JWT token
                String jwtToken = JWTController.generateJwtToken(username);

                // Set the response headers
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jwtToken.getBytes().length);

                // Send the JWT token as the response body
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(jwtToken.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private boolean checkUserExists(String username, String password) throws SQLException {
        UserController userController = new UserController();
         if (userController.checkUserExists(username, password)) {
             return true;
         } else {
             return false;
         }
    }
}
