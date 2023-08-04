package com.example.server.controllers;

import com.example.server.data_access.TweetMediaDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class TweetMediaController {
    private final TweetMediaDAO tweetMediaDAO;

    public TweetMediaController() throws SQLException {
        tweetMediaDAO= new TweetMediaDAO();
    }

    public void createMedia(String tweetId, String mediaType, String mediaUrl) throws SQLException {
        tweetMediaDAO.saveMedia(tweetId, mediaType, mediaUrl);
    }

    public void deleteMedia(int id) throws SQLException {
        tweetMediaDAO.deleteMedia(id);
    }

    public void deleteMediaByUserId(String tweetId) throws SQLException {
        tweetMediaDAO.deleteMediaByTweetId(tweetId);
    }

    public void deleteMedia() throws SQLException {
        tweetMediaDAO.deleteMedia();
    }

    public String getMediaByTweetIdAndType(String tweetId, String mediaType) throws SQLException, JsonProcessingException {
        ArrayList<String> media = tweetMediaDAO.getMediaByTweetIdAndType(tweetId, mediaType);
        if (media == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(media);
    }

    public String getMediaByTweetId(String tweetId) throws SQLException, JsonProcessingException {
        ArrayList<String> media = tweetMediaDAO.getMediaByTweetId(tweetId);
        if (media == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(media);
    }
}
