package com.sinarmin.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Hashtag {
    @JsonProperty("HashtagId")
    private String HashtagId;
    @JsonProperty("HashtagTweetsId")
    private ArrayList<String> HashtagTweetsId;

    public String getHashtagId() {
        return HashtagId;
    }

    public void setHashtagId(String hashtagId) {
        HashtagId = hashtagId;
    }

    public ArrayList<String> getTweetsId() {
        return HashtagTweetsId;
    }

    public void setHashtagTweetsId(ArrayList<String> tweetsId) {
        HashtagTweetsId = tweetsId;
    }
    public void setHashtagTweetsId(String[] tweetsId) {
        for (String tweetid : tweetsId) {
            this.HashtagTweetsId.add(tweetid);
        }
    }
    public void setHashtagTweetsId(String tweetsId) {
        this.HashtagTweetsId.add(tweetsId);
    }
}
