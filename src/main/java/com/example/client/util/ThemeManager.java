package com.example.client.util;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ThemeManager {
    public enum Theme {
        DARK, LIGHT
    }

    private static Theme currentTheme = Theme.DARK;

    public static void setCurrentTheme(Theme theme) {
        currentTheme = theme;
    }

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static void changeTheme() {
        if (currentTheme == Theme.DARK) {
            currentTheme = Theme.LIGHT;
        } else {
            currentTheme = Theme.DARK;
        }
    }

    public static void applyTheme(Parent rootNode, String fxmlPath) {
        String cssPath = currentTheme == Theme.DARK ? "/styles/darkStyle/" : "/styles/lightStyle/";
        rootNode.getStylesheets().clear();
        rootNode.getStylesheets().add(ThemeManager.class.getResource(cssPath + getStyleSheetFileName(fxmlPath)).toExternalForm());
    }

    private static String getStyleSheetFileName(String fxmlPath) {
        // Assuming fxmlPath is in the format "/com/example/client/filename.fxml"
        String fxmlFileName = fxmlPath.substring(fxmlPath.lastIndexOf("/") + 1);
        return fxmlFileName.replace(".fxml", "Style.css");
    }
}