package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Bio {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("biography")
    private String biography;

    @JsonProperty("location")
    private String location;

    @JsonProperty("website")
    private String website;

    public Bio(String userId, String biography, String location, String website) {
        this.userId = userId;
        this.biography = biography;
        this.location = location;
        this.website = website;
    }

    public Bio() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Bio{" +
                "userId='" + userId + '\'' +
                ", biography='" + biography + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
