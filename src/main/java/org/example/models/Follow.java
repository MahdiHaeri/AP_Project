package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Follow {

    @JsonProperty("follower")
    private String follower;
    @JsonProperty("followed")
    private String followed;

    public Follow(String follower, String followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Follow() {

    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String following) {
        this.followed = following;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower='" + follower + '\'' +
                ", following='" + followed + '\'' +
                '}';
    }
}
