package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.server.HttpHandlers.FollowHandler;
import org.example.server.HttpHandlers.TweetHandler;
import org.example.server.HttpHandlers.UserHandler;
import org.example.server.controllers.UserController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Date;

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
            server.createContext("/users", new UserHandler());
            server.createContext("/tweets", new TweetHandler());
            server.createContext("/follows", new FollowHandler());
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

