package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Block {


    @JsonProperty("blocker")
    private String blocker;

    @JsonProperty("blocked")
    private String blocked;

    public Block(String blocker, String blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
    }

    public Block() {

    }

    public String getBlocker() {
        return blocker;
    }

    public void setBlocker(String blocker) {
        this.blocker = blocker;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocking) {
        this.blocked = blocking;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blocker='" + blocker + '\'' +
                ", blocking='" + blocked + '\'' +
                '}';
    }

}
