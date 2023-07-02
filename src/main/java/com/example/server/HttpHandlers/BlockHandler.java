package com.example.server.HttpHandlers;

import com.example.server.controllers.BlockController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.sql.SQLException;

public class BlockHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        BlockController blockController = null;
        try {
            blockController = new BlockController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitedPath = path.split("/");

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
                if (splitedPath.length == 2) {
                    try {
                        response = blockController.getBlocks();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Extract the user ID from the path
//                    String userId = splitedPath[splitedPath.length - 1];
//                    try {
//                        response = userController.getUserById(userId);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
                }
                break;
            case "POST":
                // Process the user creation based on the request body
                String newBlock = body.toString();
                JSONObject jsonObject = new JSONObject(newBlock);
                try {
                    blockController.saveBlock(jsonObject.getString("blockerId"), jsonObject.getString("blockedId"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "this is done!";
                break;
            case "PUT":
//                String putBlock = body.toString();
//                JSONObject putJsonObject = new JSONObject(putBlock);
//                try {
//                    blockController.updateUser(putJsonObject.getString("id"), putJsonObject.getString("firstName"), putJsonObject.getString("lastName"), putJsonObject.getString("email"), putJsonObject.getString("phoneNumber"), putJsonObject.getString("password"), putJsonObject.getString("country"), new Date(putJsonObject.getLong("birthday")));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
                response = "This is the response users Put";
                break;
            case "DELETE":
                try {
                    blockController.deleteBlocks();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
