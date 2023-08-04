package com.example.server;

import com.example.server.HttpHandlers.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.SQLException;
import java.util.Date;

import static spark.Spark.*;

public class Server {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    public static void main(String[] args) {
        MediaHandler mediaHandler;
        LikeHandler likeHandler;
        LoginHandler loginHandler;
        BlockHandler blockHandler;
        TweetHandler tweetHandler;
        FollowHandler followHandler;
        UserHandler userHandler;

        try {
            mediaHandler = new MediaHandler();
            likeHandler = new LikeHandler();
            loginHandler = new LoginHandler();
            blockHandler = new BlockHandler();
            tweetHandler = new TweetHandler();
            followHandler = new FollowHandler();
            userHandler = new UserHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Initialize the Spark server
        port(8080); // Set your desired port number
      

        get("/api/users", userHandler::handleGetUser);
        get("/api/users/:username", userHandler::handleGetUserById);
        post("/api/users", userHandler::handlePostUser);
        delete("/api/users/:username", userHandler::handleDeleteUserById);
        delete("/api/users", userHandler::handleDeleteUser);
        put("/api/users/:username", userHandler::handlePutUser);


        get("/api/bios", userHandler::handleGetBio);
        get("/api/users/:username/bio", userHandler::handleGetBioById);
        post("/api/users/:username/bio", userHandler::handlePostBio);
        put("/api/users/:username/bio", userHandler::handlePutBio);
        delete("/api/users/:username/bio", userHandler::handleDeleteBioById);
        delete("/api/bios", userHandler::handleDeleteBio);


        post("/api/users/:username/follow", followHandler::handlePostFollow);
        post("/api/users/:username/unfollow", followHandler::handlePostUnfollow);
        get("/api/users/:username/followers", followHandler::handleGetFollowers);
        get("/api/users/:username/following", followHandler::handleGetFollowing);
        get("/api/follows", followHandler::handleGetFollows);


        post("/api/users/:username/block", blockHandler::handlePostBlock);
        post("/api/users/:username/unblock", blockHandler::handlePostUnblock);
        get("/api/blocks", blockHandler::handleGetBlocks);
        get("/api/users/:username/blockers", blockHandler::handleGetBlockers);
        get("/api/users/:username/blocking", blockHandler::handleGetBlocking);


        post("/api/tweets", tweetHandler::handlePostTweet);
        get("/api/tweets", tweetHandler::handleGetTweets);
        get("/api/tweets/:tweetId", tweetHandler::handleGetTweetById);
        delete("/api/tweets/:tweetId", tweetHandler::handleDeleteTweetByTweetId);
        delete("/api/tweets", tweetHandler::handleDeleteTweet);
        get("/api/users/:username/tweets", tweetHandler::handleGetTweetsByOwnerId);

        get("/api/timeline", tweetHandler::handleGetTimeline);

        post("/api/login", loginHandler::handlePostLogin);

        // TODO /api/logout

        get("/api/likes", likeHandler::handleGetLike);
        get("/api/users/:username/likes", likeHandler::handleGetLikeByUserId);
        get("/api/tweets/:tweetId/likes", likeHandler::handleGetLikeByTweetId);
        post("/api/tweets/:tweetId/like", likeHandler::handlePostLike);
        post("/api/tweets/:tweetId/unlike", likeHandler::handlePostUnlike);

        get("/api/users/:username/retweets", (request, response) -> {
            return "GET /api/users/:username/retweets";
        });

        post("/api/tweets/:tweetId/retweet", (request, response) -> {
            return "POST /api/tweets/:tweetId/retweet";
        });

        get("/api/hashtags/:hashtag", (request, response) -> {
            return "GET /api/hashtags/:hashtag";
            // todo fix bug in HashtagController
        });

        get("/api/search/users", (request, response) -> {
            return "GET /api/search/users";
        });

        get("/api/search/tweets", (request, response) -> {
            return "GET /api/search/tweets";
        });

        // todo /api/notifications

        // todo /api/notifications/:notificationId

        get("/api/direct-messages", (request, response) -> {
            return "GET /api/direct-messages";
        });

        // todo /api/direct-message/:recipientUsername
        // todo /api/trending
        // todo /api/recommendations/users
        // todo /api/analytics/tweets

        get("/api/users/:username/profile-image", mediaHandler::handleGetMedia);
        get("/api/users/:username/header-image", mediaHandler::handleGetMedia);
        post("/api/users/:username/profile-image", mediaHandler::handlePostMedia);
        post("/api/users/:username/header-image", mediaHandler::handlePostMedia);

        get("/api/tweets/:tweetId/tweet-image", mediaHandler::handleGetMedia);
        post("/api/tweets/:tweetId/tweet-image", mediaHandler::handlePostMedia);

        // Add the notFound route to handle unmatched paths
        notFound((request, response) -> {
            response.status(404);
            return "Not found";
        });
    }
}

    /*
        Here's a comprehensive list of API endpoints you may need for your Twitter-like application:

        1. **User Management:**
           - `/api/users` (POST): Create a new user account.
           - `/api/users/{username}` (GET): Retrieve user profile by username.
           - `/api/users/{username}` (PUT): Update user profile information.
           - `/api/users/{username}` (DELETE): Delete a user account.

        2. **Authentication and Authorization:**
           - `/api/login` (POST): Authenticate user login and issue an access token.
           - `/api/logout` (POST): Invalidate the user's access token and log them out.

        3. **Tweet Management:**
           - `/api/tweets` (GET): Retrieve a list of tweets from the user's feed (tweets from people they follow).
           - `/api/tweets/{tweetId}` (GET): Get a specific tweet by its ID.
           - `/api/tweets` (POST): Create a new tweet.
           - `/api/tweets/{tweetId}` (PUT): Update an existing tweet (if allowed by the user).
           - `/api/tweets/{tweetId}` (DELETE): Delete a tweet (if allowed by the user).

        4. **User Timeline:**
           - `/api/users/{username}/tweets` (GET): Retrieve tweets posted by a specific user.

        5. **Follow and Unfollow:**
           - `/api/users/{username}/follow` (POST): Follow a user.
           - `/api/users/{username}/unfollow` (POST): Unfollow a user.

        6. **Like and Unlike:**
           - `/api/tweets/{tweetId}/like` (POST): Like a tweet.
           - `/api/tweets/{tweetId}/unlike` (POST): Unlike a tweet.

        7. **Retweet:**
           - `/api/tweets/{tweetId}/retweet` (POST): Retweet a tweet.

        8. **Hashtags:**
           - `/api/hashtags/{hashtag}` (GET): Retrieve tweets containing a specific hashtag.

        9. **Search:**
           - `/api/search/users` (GET): Search for users by their username or display name.
           - `/api/search/tweets` (GET): Search for tweets based on keywords or hashtags.

        10. **Bio Management:**
           - `/api/users/{username}/bio` (GET): Retrieve the user's bio information.
           - `/api/users/{username}/bio` (PUT): Update the user's bio information.

            11. **Block and Unblock:**
                - `/api/users/{username}/block` (POST): Block a user, preventing them from following or interacting with the blocking user.
                - `/api/users/{username}/unblock` (POST): Unblock a previously blocked user.

            12. **User Followers and Following:**
                - `/api/users/{username}/followers` (GET): Retrieve a list of users who follow a specific user.
                - `/api/users/{username}/following` (GET): Retrieve a list of users whom a specific user follows.

            13. **Notifications:**
                - `/api/notifications` (GET): Retrieve a list of notifications for the authenticated user (e.g., new followers, mentions, likes).
                - `/api/notifications/{notificationId}` (DELETE): Dismiss a specific notification.

            14. **Direct Messages:**
                - `/api/direct-messages` (GET): Retrieve a list of direct messages for the authenticated user.
                - `/api/direct-messages/{recipientUsername}` (GET): Retrieve direct messages exchanged with a specific user.
                - `/api/direct-messages/{recipientUsername}` (POST): Send a direct message to a specific user.

            15. **Trending Topics:**
                - `/api/trending` (GET): Retrieve a list of current trending topics or hashtags.

            16. **User Recommendations:**
                - `/api/recommendations/users` (GET): Get recommendations for users to follow based on interests or connections.

            17. **User Interactions:**
                - `/api/users/{username}/likes` (GET): Retrieve a list of tweets liked by a specific user.
                - `/api/users/{username}/retweets` (GET): Retrieve a list of tweets retweeted by a specific user.

            18. **User Settings:**
                - `/api/settings` (GET): Retrieve the authenticated user's account settings.
                - `/api/settings` (PUT): Update the authenticated user's account settings (e.g., privacy, notification preferences).

            19. **User Verification:**
                - `/api/users/{username}/verify` (POST): Verify a user's account (if you implement a verification system).

            20. **Analytics:**
                - `/api/analytics/tweets` (GET): Retrieve analytics for a user's tweets (e.g., impressions, engagement).

            21. **Media Management:**
                - `/api/users/{username}/profile-image (POST): Upload the profile image for a specific user.
                - `/api/users/{username}/header-image (POST): Upload the header image for a specific user.
                - `/api/users/{username}/profile-image (GET): Retrieve the URL or filename of the user's profile image.
                - `/api/users/{username}/header-image (GET): Retrieve the URL or filename of the

         */
