package com.example.server.HttpHandlers;

import com.example.server.controllers.UserMediaController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.servlet.ServletException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class UserMediaHandler {
    private final UserMediaController userMediaController;

    public UserMediaHandler() throws SQLException {
        userMediaController = new UserMediaController();
    }

    public Object handlePostMedia(Request request, Response response) throws IOException, ServletException {
        String userId = request.params(":username");
        String mediaGroup = request.pathInfo().split("/")[4];
        String fileType = request.headers("Content-Type");

        // make unique file name
        String mediaUrl = "assets/" + mediaGroup + "/" + userId + "/" + userId + "_" + new Date().getTime() + "." + fileType.split("/")[1];

        // get file bytes
        byte[] fileByte = request.bodyAsBytes();
        File file = new File(mediaUrl);

        // create file if it doesn't exist
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        file.createNewFile();

        // write file bytes to file
        java.nio.file.Files.write(file.toPath(), fileByte);

        try {
            userMediaController.createMedia(userId, mediaGroup, mediaUrl);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        response.status(201);
        response.body("Media saved successfully");
        return response.body();
    }

    public Object handleGetMedia(Request request, Response response) throws IOException, ServletException, SQLException {
        String userId = request.params(":username");
        String mediaGroup = request.pathInfo().split("/")[4];
        String userMedia = userMediaController.getMediaByUserIdAndType(userId, mediaGroup);
        String mediaUrl = null;

        if (userMedia.equals("[]")) {
            if (mediaGroup.equals("profile-image")) {
                mediaUrl = "src/main/resources/images/default_profile_image.jpeg";
            } else if (mediaGroup.equals("header-image")) {
                mediaUrl = "src/main/resources/images/default_header_image.png";
            } else {
                response.status(400);
                response.body("Media not found.");
                return response.body();
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonArray = mapper.readTree(userMedia);
            mediaUrl = jsonArray.get(jsonArray.size() - 1).asText();
        }

        File file = new File(mediaUrl);
        if (!file.exists() || !file.isFile()) {
            response.status(400);
            response.body("Media file not found or is not a file.");
            return response.body();
        }

        writeMediaContentToResponse(file, response);

        response.status(200);
        response.body("Media retrieved successfully");
        return response.body();
    }

    public void writeMediaContentToResponse(File file, Response response) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.raw().getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}