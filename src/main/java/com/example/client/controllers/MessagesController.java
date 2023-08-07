package com.example.client.controllers;

import com.example.client.util.ThemeManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagesController implements Initializable {
    @FXML
    private BorderPane rootBp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ThemeManager.applyTheme(rootBp,  url.getPath());
    }
}
