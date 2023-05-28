package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Follow {

    @JsonProperty("follower")
    private String follower;
    @JsonProperty("following")
    private String following;

    public Follow(String follower, String following) {
        this.follower = follower;
        this.following = following;
    }

    public Follow() {

    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower='" + follower + '\'' +
                ", following='" + following + '\'' +
                '}';
    }
}
