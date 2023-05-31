package org.example.server.HttpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.example.server.controllers.UserController;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitedPath = path.split("/");
        switch (method) {
            case "GET":
                // if (path.matches("^/users/\\d+$")) {
                //     // Extract the user ID from the path
                //     String[] pathSegments = path.split("/");
                //     String userId = pathSegments[pathSegments.length - 1];

                //     try {
                //         UserController userController = new UserController();
                //         response = userController.getUserById(userId);
                //     } catch (SQLException e) {
                //         throw new RuntimeException(e);
                //     }
                // } else {
                //     try {
                //         UserController userController = new UserController();
                //         response = userController.getUsers();
                //     } catch (SQLException e) {
                //         throw new RuntimeException(e);
                //     }
                // }
                if (splitedPath.length == 2) {
                    try {
                        UserController userController = new UserController();
                        response = userController.getUsers();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
                    String userId = splitedPath[splitedPath.length - 1];

                    try {
                        UserController userController = new UserController();
                        response = userController.getUserById(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "POST":
                response = "This is the response users Post";
                break;
            case "PUT":
                response = "This is the response users Put";
                break;
            case "DELETE":
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