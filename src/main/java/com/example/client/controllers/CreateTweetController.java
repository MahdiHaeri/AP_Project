package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpHeaders;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import com.example.client.util.ThemeManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTweetController implements Initializable {

    @FXML
    private BorderPane rootBp;
    String tweetType;

    @FXML
    private Button tweetBtn;

    @FXML
    private VBox mainVbox;

    @FXML
    private TextArea textArea;

    @FXML
    private FontAwesomeIconView videoIcon;

    @FXML
    private FontAwesomeIconView imageIcon;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private HBox imageContainerHbox;

    private QuoteTweetController quoteTweetController;

    public QuoteTweetController getQuoteTweetController() {
        return quoteTweetController;
    }

    public void setQuoteTweetController(QuoteTweetController quoteTweetController) {
        this.quoteTweetController = quoteTweetController;
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public String getTweetType() {
        return tweetType;
    }

    public void setTweetType(String tweetType) {
        this.tweetType = tweetType;
    }

    public String getTweetBtn() {
        return tweetBtn.getText();
    }

    public void setTweetBtn(String text) {
        this.tweetBtn.setText(text);
    }

    public Image getAvatar() {
        return avatar.getImage();
    }

    public void setAvatar(Image image) {
        this.avatar.setImage(image);
    }

    @FXML
    void onImageIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(250);
            imageView.setFitHeight(180);
            // radius of the image
            // Create a Rectangle with rounded corners
            Rectangle clipRectangle = new Rectangle(250, 180);
            clipRectangle.setArcWidth(20);
            clipRectangle.setArcHeight(20);
            imageView.setClip(clipRectangle);
            imageContainerHbox.getChildren().add(imageView);
            // if Hbox have 4 images disable the imageIcon
            if (imageContainerHbox.getChildren().size() == 4) {
                imageIcon.setDisable(true);
                imageIcon.setStyle("-fx-opacity: 0.5;");
            }

        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {
        if (textArea.getText().isEmpty()) {
            return;
        }

        switch (getTweetType()) {
            case "tweet" -> createTweet();
            case "reply" -> createReply();
            case "quote" -> createQuote();
        }


        textArea.setText("");

        textArea.getScene().getWindow().hide();

    }

    private void createQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());

        String body = "{\"text\": \"" + textArea.getText() + "\"}";
        try {
            HttpResponse response = HttpController.sendRequest("http://localhost:8080/api/tweets/" + quoteTweetController.getTweetId() + "/quote", HttpMethod.POST, body, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createReply() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());

        String body = "{\"text\": \"" + textArea.getText() + "\"}";
        try {
            HttpResponse response = HttpController.sendRequest("http://localhost:8080/api/tweets/" + quoteTweetController.getTweetId() + "/reply", HttpMethod.POST, body, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTweet() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());

        String body = "{\"text\": \"" + textArea.getText() + "\"}";
        try {
            HttpResponse response = HttpController.sendRequest("http://localhost:8080/api/tweets", HttpMethod.POST, body, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onVideoIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)imageIcon.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ThemeManager.applyTheme(rootBp, url.getPath());
        setTweetType("tweet");

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
    }

    public void addQuote(String quoteTweetId) {
        setTweetType("quote");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/quoteTweet.fxml"));
        Parent quoteTweetRoot = null;
        try {
            quoteTweetRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuoteTweetController replyTweetController = fxmlLoader.getController();
        setQuoteTweetController(replyTweetController);
        replyTweetController.fillQuote(quoteTweetId);
        replyTweetController.setMainController(mainController);
        mainVbox.getChildren().add(quoteTweetRoot);
    }

    public void addReply(String replyTweetId) {
        setTweetType("reply");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/quoteTweet.fxml"));
        Parent replyTweetRoot = null;
        try {
            replyTweetRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuoteTweetController replyTweetController = fxmlLoader.getController();
        setQuoteTweetController(replyTweetController);
        replyTweetController.fillQuote(replyTweetId);
        replyTweetController.setMainController(mainController);
        mainVbox.getChildren().add(0, replyTweetRoot);
    }
}