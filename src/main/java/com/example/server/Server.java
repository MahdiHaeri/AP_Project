package com.example.server;

import com.sun.net.httpserver.HttpServer;
import com.example.server.HttpHandlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
//        try {
//            UserController userController = new UserController();
//            userController.createUser("mahdi", "mahdi", "haeri", "mahdihaerim@gmail.com", "123123123", "123", "Iran", new Date());
//            userController.createUser("1", "ali", "haeri", "mahdihaerim@gmail.com", "123123123", "123", "Iran", new Date());
//            userController.createUser("2", "sadegh", "haeri", "mahdihaerim@gmail.com", "123123123", "123", "Iran", new Date());
//            userController.createUser("3", "javad", "haeri", "mahdihaerim@gmail.com", "123123123", "123", "Iran", new Date());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        System.exit(0);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/api/users", new UserHandler());
            server.createContext("/api/login", new LoginHandler());
            server.createContext("/api/tweets", new TweetHandler());
            server.createContext("/api/bios", new BioHandler());
            server.createContext("/api/follows", new FollowHandler());
            server.createContext("/api/media", new MediaHandler());
            server.createContext("/api/hashtag", new HashtagHandler());
            server.createContext("/api/blocks", new BlockHandler());
            server.start();

            /*
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
            */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

