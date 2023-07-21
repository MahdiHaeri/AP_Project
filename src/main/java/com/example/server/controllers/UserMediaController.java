package com.example.server.controllers;

import com.example.server.data_access.UserMediaDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserMediaController {
    private final UserMediaDAO userMediaDAO;

    public UserMediaController() throws SQLException {
        userMediaDAO = new UserMediaDAO();
    }

    public void createMedia(String userId, String mediaType, String mediaUrl) throws SQLException {
        userMediaDAO.saveMedia(userId, mediaType, mediaUrl);
    }

    public void deleteMedia(int id) throws SQLException {
        userMediaDAO.deleteMedia(id);
    }

    public void deleteMediaByUserId(String userId) throws SQLException {
        userMediaDAO.deleteMediaByUserId(userId);
    }

    public void deleteMedia() throws SQLException {
        userMediaDAO.deleteMedia();
    }

    public String getMediaByUserIdAndType(String userId, String mediaType) throws SQLException, JsonProcessingException {
        ArrayList<String> media = userMediaDAO.getMediaByUserIdAndType(userId, mediaType);
        if (media == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(media);
    }

    public String getMediaByUserId(String userId) throws SQLException, JsonProcessingException {
        ArrayList<String> media = userMediaDAO.getMediaByUserId(userId);
        if (media == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(media);
    }
}
