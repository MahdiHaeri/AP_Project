# Twitter-Like Application with Java and JavaFX

## Description

This is a Twitter-like application built using Java and JavaFX for the client-side interface. The application follows an MVC (Model-View-Controller) architecture and interacts with a PostgreSQL database through RESTful API endpoints. The server handles incoming requests, delegates them to the appropriate controllers, which in turn interact with the data access layer to process and retrieve data from the database.

## Table of Contents

- [Installation](#installation)
- [Features](#features)
- [Endpoints](#endpoints)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [License](#license)
- [Contributing](#contributing)

## Installation

To run the application, follow these steps:

1. Clone the repository to your local machine.

   ```bash
   git clone git@github.com:MahdiHaeri/AP_Project.git
   ```

2. Set up the PostgreSQL database and configure the connection details in the application. Open the `DatabaseConnectionManager.java` file located at `com/example/server/data_access/DatabaseConnectionManager.java`.

   ```java
   package com.example.server.data_access;

   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.SQLException;

   public class DatabaseConnectionManager {
       private static final String JDBC_URL = "jdbc:postgresql://your_postgresql_host:your_port/your_database_name";
       private static final String USERNAME = "your_username";
       private static final String PASSWORD = "your_password";

       private static Connection connection;

       private DatabaseConnectionManager() {
           // Private constructor to prevent instantiation
       }

       public static Connection getConnection() throws SQLException {
           if (connection == null || connection.isClosed()) {
               connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
           }
           return connection;
       }
   }
   ```

3. In the `DatabaseConnectionManager.java` file, modify the following lines to match your PostgreSQL database configuration:

   ```java
   private static final String JDBC_URL = "jdbc:postgresql://your_postgresql_host:your_port/your_database_name";
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

4. Build and run the server component.

5. Build and run the client application.

Make sure to replace the placeholders (e.g., `your_username`, `your_database_name`, etc.) with the actual values relevant to your PostgreSQL database setup.

After following these steps, users will be able to set up the database connection correctly and run your application smoothly.

## Features

1. User Management
   - Create, retrieve, update, and delete users.
   - Manage user bios and profile images.
   - Follow/unfollow other users.
   - Block/unblock other users.

2. Tweet Management
   - Create, retrieve, and delete tweets.
   - Reply, retweet, and quote tweets.
   - Like and unlike tweets.

3. Timeline
   - View the timeline of tweets from followed users.

4. User Profile
   - View user profiles.
   - Edit own profile details.

5. Messaging and Notifications
   - View and send messages.
   - View notifications.

## Endpoints

Below are the available API endpoints in the server:

```java
// User Endpoints
GET /api/users
GET /api/users/:username
POST /api/users
DELETE /api/users/:username
DELETE /api/users
PUT /api/users/:username

// Bio Endpoints
GET /api/bios
GET /api/users/:username/bio
POST /api/users/:username/bio
PUT /api/users/:username/bio
DELETE /api/users/:username/bio
DELETE /api/bios

// Follow Endpoints
POST /api/users/:username/follow
POST /api/users/:username/unfollow
GET /api/users/:username/followers
GET /api/users/:username/following
GET /api/follows

// Block Endpoints
POST /api/users/:username/block
POST /api/users/:username/unblock
GET /api/blocks
GET /api/users/:username/blockers
GET /api/users/:username/blocking

// Tweet Endpoints
POST /api/tweets
GET /api/tweets
GET /api/tweets/:tweetId
DELETE /api/tweets/:tweetId
DELETE /api/tweets
GET /api/users/:username/tweets
GET /api/timeline
POST /api/tweets/:tweetId/retweet
POST /api/tweets/:tweetId/quote
POST /api/tweets/:tweetId/reply
GET /api/tweets/:tweetId/replies
GET /api/tweets/:tweetId/retweets
GET /api/tweets/:tweetId/quotes

// Login Endpoint
POST /api/login

// Like Endpoints
GET /api/likes
GET /api/users/:username/likes
GET /api/tweets/:tweetId/likes
POST /api/tweets/:tweetId/like
POST /api/tweets/:tweetId/unlike

// Media Endpoints
GET /api/users/:username/profile-image
GET /api/users/:username/header-image
POST /api/users/:username/profile-image
POST /api/users/:username/header-image
GET /api/tweets/:tweetId/tweet-image
POST /api/tweets/:tweetId/tweet-image
```

## Screenshots

| Login | Signup |
| :---: | :----: |
|<img width="1512" alt="login" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/6045e743-d40f-45b3-81cf-58e68d18a702"> | <img width="1512" alt="signup" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/7cd9612c-a00b-4902-973d-48131bc85b7f"> |
| Light | Dark |
| <img width="1512" alt="light_timeline" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/62cd6489-3e52-44db-8ed6-1abdbaf5e6b0"> | <img width="1512" alt="dark_timeline" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/4a755421-0e8b-45dd-94ab-2fc034bcd2ab"> |
| <img width="1512" alt="light_notification" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/cf3620f6-dcdd-4b86-b7af-736084853b07"> | <img width="1512" alt="dark_notification" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/081fb888-7d3b-4a70-8e38-9ea59311716b"> |
| <img width="1512" alt="light_messages" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/3e2fcbf3-b33e-408f-8d5c-e84f14beb9cf"> | <img width="1512" alt="dark_messages" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/20282d2b-5cc2-46e3-8a5b-f586e8af8fe1"> |
| <img width="1512" alt="light_bookmark" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/9c2f09d3-a075-4ac9-accc-5896d95e7d42"> | <img width="1512" alt="dark_bookmark" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/2c9de286-2bc5-4972-aef3-32406e483c19"> |
| <img width="1512" alt="light_profile2" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/190549f3-3e72-4099-bb07-ef0f7d84ef5f"> | <img width="1512" alt="dark_profile2" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/abea2dda-6480-4d8d-a92b-fc5a629594dd"> |
| <img width="1512" alt="light_editProfile2" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/2dec8d10-fc4c-4e78-b84a-aea761968c6b"> | <img width="1512" alt="dark_editProfile2" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/326be353-7835-4bed-a152-7e07261c2dee"> |
| <img width="1512" alt="light_follow" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/9073448c-c3ba-4426-8d2a-978908f88d31"> | <img width="1512" alt="dark_follow" src="https://github.com/MahdiHaeri/AP_Project/assets/73737391/8900125b-a678-4e30-b4bf-50b0d88a2a46"> |

## Technologies Used

- Java
- JavaFX
- PostgreSQL
- HTTP Server
- RESTful API
- JWT authentication
- MVC Architecture
- JSON
- Data Access Object (DAO) pattern

## License

The Twitter Simulation project is licensed under the MIT License. You are free to modify and distribute the project according to the terms of the license.

## Contributing

Contributions to the Twitter Simulation project are welcome! If you want to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure that the codebase passes all tests.
4. Submit a pull request describing your changes.
