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

    opens com.example.ap_project to javafx.fxml;
    opens com.example.server.models to com.fasterxml.jackson.databind; // Add this line

    exports com.example.ap_project;
}
