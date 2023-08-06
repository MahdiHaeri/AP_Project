package com.example.client.controllers;

import com.example.client.util.JWTController;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button changeThemeBtn;

    @FXML
    private ImageView themeImageView;

    @FXML
    private Button bookmarksBtn;

    @FXML
    private Button exploreBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button listsBtn;

    @FXML
    private Button messagesBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Button notificationBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private BorderPane rootBp;

    @FXML
    private Button tweetBtn;


    @FXML
    private GNAvatarView avatar;

    @FXML
    private Button logoutBtn;

    public BorderPane getRootBp() {
        return rootBp;
    }

    public void setCenter(Parent parent) {
        rootBp.setCenter(parent);
    }

    public Image getThemeImageView() {
        return themeImageView.getImage();
    }

    public void setThemeImageView(Image image) {
        this.themeImageView.setImage(image);
    }

    @FXML
    void onChangeThemeBtnAction(ActionEvent event) {
        Image image = new Image(getClass().getResourceAsStream("/images/icons8_dark_mode_48.png"));
        setThemeImageView(image);
        changeThemeBtn.setText("Dark mode");
    }

    @FXML
    void onBookmarksBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/bookmarks.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onExploreBtnAction(ActionEvent event) {
        try {
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            TimelineController timelineController = fxmlLoader.getController();
            timelineController.setMainController(this);
            timelineController.fillTimeline(username);
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHomeBtnAction(ActionEvent event) {
        try {
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            TimelineController timelineController = fxmlLoader.getController();
            timelineController.setMainController(this);
            timelineController.fillTimeline(username);
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onListsBtnAction(ActionEvent event) {

    }

    @FXML
    void onMessagesBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/messages.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onMoreBtnAction(ActionEvent event) {

    }

    @FXML
    void onNotificatinosBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/notifications.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onProfileBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/profile.fxml"));
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
            Parent profileRoot = fxmlLoader.load();
            ProfileController profileController = fxmlLoader.getController();
            profileController.fillProfile(username);
            profileController.setMainController(this);
            rootBp.setCenter(profileRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/createTweet.fxml"));
            Stage stage = new Stage();
            Parent createTweetRoot = fxmlLoader.load();
            stage.setTitle("Create Tweet");
            stage.setScene(new Scene(createTweetRoot));
            stage.show();
            // when stage closed, refresh the timeline to show the new tweet

            stage.setOnHidden(e -> {
                initialize(null, null);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            TimelineController timelineController = fxmlLoader.getController();
            timelineController.setMainController(this);
            timelineController.fillTimeline(JWTController.getSubjectFromJwt(JWTController.getJwtKey()));
            rootBp.setCenter(timelineRoot);

            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/example/client/trends.fxml"));
            Parent trendsRoot = fxmlLoader2.load();

            try {

                URL url2 = new URL("http://localhost:8080/api/users/" + JWTController.getSubjectFromJwt(JWTController.getJwtKey()) + "/profile-image");
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                InputStream inputStream = conn.getInputStream();
                Image image = new Image(inputStream);
                avatar.setImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            rootBp.setRight(trendsRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onLogoutBtnAction(ActionEvent event) {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/com/example/client/login.fxml"));
            Scene loginScene = new Scene(loginPage);
            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setMaximized(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JWTController.removeJwtKey();
    }
}
