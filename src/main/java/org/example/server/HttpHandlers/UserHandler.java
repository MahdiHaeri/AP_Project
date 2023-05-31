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
        String response = "";
        switch (method) {
            case "GET":
                try {
                    UserController userController = new UserController();
                    response = userController.getUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
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
