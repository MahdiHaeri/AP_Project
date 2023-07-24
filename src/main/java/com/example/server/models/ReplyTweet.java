package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ReplyTweet extends Tweet {

        @JsonProperty("parentTweetId")
        private String parentTweetId;

        public ReplyTweet(String tweetId, String ownerId, String text, int replyCount, int retweetCount, int likeCount, String replyTweetId) {
            super(tweetId, ownerId, text, replyCount, retweetCount, likeCount);
            this.parentTweetId = replyTweetId;
        }

        public String getParentTweetId() {
            return parentTweetId;
        }

        public void setParentTweetId(String parentTweetId) {
            this.parentTweetId = parentTweetId;
        }

        @Override
        public String toString() {
            return "ReplyTweet{" +
                    "replyTweetId='" + parentTweetId + '\'' +
                    '}';
        }
}
