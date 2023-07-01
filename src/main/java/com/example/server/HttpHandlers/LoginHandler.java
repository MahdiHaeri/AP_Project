package com.example.server.HttpHandlers;

import com.example.server.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;

import com.example.server.controllers.UserController;

public class LoginHandler implements HttpHandler {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 3600000; // 1 hour

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
                String jwtToken = generateJwtToken(username);

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


    private String generateJwtToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
