package com.sinarmin.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinarmin.server.data_access.HashtagDAO;
import com.sinarmin.server.data_access.TweetDAO;
import com.sinarmin.server.models.Hashtag;
import com.sinarmin.server.models.Tweet;
import com.sinarmin.server.models.User;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HashtagController {
    private final HashtagDAO hashtagDAO;
    public HashtagController() throws SQLException {
        hashtagDAO = new HashtagDAO();
    }
    public void createHashtag(String id) throws SQLException {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagId(id);
        hashtag.setHashtagTweetsId(new ArrayList<>());
        hashtagDAO.saveHashtag(hashtag);
    }
    public void updateHashtag(String id, String[] tweets_id) throws SQLException {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagId(id);
        hashtag.setHashtagTweetsId(tweets_id);
        hashtagDAO.updateHashtag(hashtag);
    }
    public void updateHashtag(Hashtag hashtag) throws SQLException {
        hashtagDAO.updateHashtag(hashtag);
    }
    public void deleteHashtag(String id) throws SQLException {
        hashtagDAO.deleteHashtag(id);
    }

    public Hashtag getHashtag(String id) throws SQLException {
        return hashtagDAO.getHashtag(id);
    }
    public String JsonGetHashtag(String id) throws SQLException, JsonProcessingException {
        Hashtag hashtag = hashtagDAO.getHashtag(id);

        if (hashtag == null)
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(hashtag);
        return response;
    }
}
