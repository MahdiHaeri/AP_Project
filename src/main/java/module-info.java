module com.example.ap_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.fasterxml.jackson.annotation;
    requires jdk.httpserver;
    requires java.sql;
    requires json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.media;
    requires GNAvatarView;
    requires jjwt.api;
    requires de.jensd.fx.glyphs.fontawesome;
    requires spark.core;
    requires javax.servlet.api;
    requires javafx.swing;
    requires MaterialFX;

//    requires javafx.base;
//    requires javafx.graphics;
    // add icon pack modules

    opens com.example.client to javafx.fxml;
    opens com.example.server.models to com.fasterxml.jackson.databind; // Add this line

    exports com.example.client;
    exports com.example.client.controllers;
    opens com.example.client.controllers to javafx.fxml;
    exports com.example.client.util;
    opens com.example.client.util to javafx.fxml;
}
