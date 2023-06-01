package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Bio {

    @JsonProperty("id")
    private String id;
    @JsonProperty("biography")
    private String biography;

    @JsonProperty("location")
    private String location;

    @JsonProperty("website")
    private String website;

    public Bio(String id, String biography, String location, String website) {
        this.id = id;
        this.biography = biography;
        this.location = location;
        this.website = website;
    }

    public Bio() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", biography='" + biography + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
