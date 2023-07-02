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
            server.createContext("/bios", new BioHandler());
            server.createContext("/users", new UserHandler());
            server.createContext("/tweets", new TweetHandler());
            server.createContext("/follows", new FollowHandler());
            server.createContext("/login", new LoginHandler());
            server.createContext("/media", new MediaHandler());
            server.createContext("/hashtag", new HashtagHandler());
            server.createContext("/blocks", new BlockHandler());
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

