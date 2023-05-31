package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.server.models.Follow;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
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

