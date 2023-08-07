package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpHeaders;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import com.example.client.util.ThemeManager;
import com.example.client.util.TimestampController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class TweetController implements Initializable {
    private boolean isLiked = false;

    private boolean isRetweeted = false;

    private boolean isReply = false;

    private boolean isQuoted = false;

    private String tweetId;

    @FXML
    private VBox rootVbox;

    @FXML
    private BorderPane rootBp;

    @FXML
    private VBox leftVbox;

    @FXML
    private GNAvatarView avatarView;

    @FXML
    private Button likeBtn;

    @FXML
    private FontAwesomeIconView replyIcon;

    @FXML
    private FontAwesomeIconView retweetIcon;

    @FXML
    private FontAwesomeIconView likeIcon;

    @FXML
    private FontAwesomeIconView quoteIcon;

    @FXML
    private Label ownerNameLbl;

    @FXML
    private Label ownerUsernameLbl;

    @FXML
    private Button replyBtn;

    @FXML
    private Button retweetBtn;

    @FXML
    private Label retweetedNameLbl;

    @FXML
    private Button quoteBtn;

    @FXML
    private VBox contentContainer;

    @FXML
    private Text textMessageText;

    @FXML
    private Label timestampLbl;

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public boolean isReplied() {
        return isReply;
    }

    public void setReplied(boolean reply) {
        isReply = reply;
    }

    public boolean isQuoted() {
        return isQuoted;
    }

    public void setQuoted(boolean quoted) {
        isQuoted = quoted;
    }

    public Image getAvatarView() {
        return avatarView.getImage();
    }

    public double getAvatarX() {
        return avatarView.getLayoutX();
    }

    public double getAvatarY() {
        return avatarView.getLayoutY();
    }

    public void setAvatarView(Image image) {
        this.avatarView.setImage(image);
    }

    public String getLikeBtn() {
        return likeBtn.getText();
    }
//
    public void setLikeBtn(String text) {
        this.likeBtn.setText(text);
    }

    public String getOwnerNameLbl() {
        return ownerNameLbl.getText();
    }

    public void setOwnerNameLbl(String text) {
        this.ownerNameLbl.setText(text);
    }

    public String getOwnerUsernameLbl() {
        return ownerUsernameLbl.getText().substring(1);
    }

    public void setOwnerUsernameLbl(String text) {
        this.ownerUsernameLbl.setText(text);
    }

    public String getReplyBtn() {
        return replyBtn.getText();
    }

    public void setReplyBtn(String text) {
        this.replyBtn.setText(text);
    }

    public String getRetweetBtn() {
        return retweetBtn.getText();
    }

    public void setRetweetBtn(String text) {
        this.retweetBtn.setText(text);
    }

    public String getRetweetedNameLbl() {
        return retweetedNameLbl.getText();
    }

    public void setRetweetedNameLbl(String text) {
        this.retweetedNameLbl.getText();
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    private TimelineController timelineController;

    public TimelineController getTimelineController() {
        return timelineController;
    }

    public void setTimelineController(TimelineController timelineController) {
        this.timelineController = timelineController;
    }

    public String getQuoteBtn() {
        return quoteBtn.getText();
    }

    public void setQuoteBtn(String text) {
        this.quoteBtn.setText(text);
    }

    public String getTextMessageText() {
        return textMessageText.getText();
    }

    public void setTextMessageText(String text) {
        this.textMessageText.setText(text);
    }

    public String getTimestampLbl() {
        return timestampLbl.getText();
    }

    public void setTimestampLbl(String text) {
        this.timestampLbl.setText(text);
    }


// event handling :

    @FXML
    void onAvatarViewClicked(MouseEvent event) {
        String username = getOwnerUsernameLbl();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/profile.fxml"));
            Parent profileRoot = fxmlLoader.load();
            ProfileController profileController = fxmlLoader.getController();
            profileController.fillProfile(username);
            profileController.setMainController(mainController);
            mainController.setCenter(profileRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onLikeBtnAction(ActionEvent event) {
        String tweetId = getTweetId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JWTController.getJwtKey());
        HttpResponse response;
        if (isLiked) {
            try {
                response = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetId + "/unlike", HttpMethod.POST, "", headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            decrementLikeCount();
            setTweetUnliked();
            setLiked(false);
        } else {
            try {
                response = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetId + "/like", HttpMethod.POST, "", headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            incrementLikeCount();
            setTweetLiked();
            setLiked(true);
        }
    }

    @FXML
    void onReplyBtnAction(ActionEvent event) {
        String tweetId = getTweetId();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/createTweet.fxml"));
            Stage stage = new Stage();
            Parent createReplyTweetRoot = fxmlLoader.load();
            CreateTweetController createTweetController = fxmlLoader.getController();
            createTweetController.setMainController(getMainController());
            createTweetController.addReply(tweetId);
            stage.setTitle("Create Reply Tweet");
            stage.setScene(new Scene(createReplyTweetRoot));
            stage.show();
            // when stage closed, refresh the timeline to show the new tweet

            stage.setOnHidden(e -> {
                getTimelineController().updateTimeline();
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onRetweetBtnAction(ActionEvent event) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());
        String tweetId = getTweetId();
        HttpResponse response;
//        String body = "";
        String body = "{\"text\": \" this is the text of retweet\"}";
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetId + "/retweet", HttpMethod.POST, body, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            timelineController.updateTimeline();
        } else {
            System.out.println("error in retweeting");
        }
    }

    @FXML
    void onQuoteBtnAction(ActionEvent event) {
        String tweetId = getTweetId();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/createTweet.fxml"));
            Stage stage = new Stage();
            Parent createReplyTweetRoot = fxmlLoader.load();
            CreateTweetController createTweetController = fxmlLoader.getController();
            createTweetController.setMainController(getMainController());
            createTweetController.addQuote(tweetId);
            stage.setTitle("Create Reply Tweet");
            stage.setScene(new Scene(createReplyTweetRoot));
            stage.show();
            // when stage closed, refresh the timeline to show the new tweet

            stage.setOnHidden(e -> {
                getTimelineController().updateTimeline();
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ThemeManager.applyTheme(rootVbox, url.getPath());
    }

    public void prepareTweet(String tweetId) {
        HttpResponse tweetResponse;
        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetId, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetJson;
        try {
            tweetJson = objectMapper.readTree(tweetResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (tweetJson.has("parentTweetId")) {
            prepareTweet(tweetJson.get("parentTweetId").asText());
            addReply(tweetId);
        } else if (tweetJson.has("retweetId")) {
            prepareTweet(tweetJson.get("retweetId").asText());
            addRetweetHeader(tweetJson.get("ownerId").asText());
        } else if (tweetJson.has("quoteTweetId")) {
            fillTweet(tweetId);
            addQuote(tweetJson.get("quoteTweetId").asText());
        } else {
            fillTweet(tweetId);
        }
    }

    public void fillTweet(String tweetId) {
        // Set the tweet information on the controller
        HttpResponse tweetResponse;
        HttpResponse userResponse;
        HttpResponse likeResponse;
        HttpResponse replyResponse;
        HttpResponse retweetResponse;
        HttpResponse quoteResponse;

        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetId, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetJson;
        JsonNode usersJson;
        try {
            tweetJson = objectMapper.readTree(tweetResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            userResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText(), HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            usersJson = objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        setTweetId(tweetJson.get("tweetId").asText());
        setTextMessageText(tweetJson.get("text").asText());
        setOwnerUsernameLbl("@" + tweetJson.get("ownerId").asText());
        setReplyBtn(tweetJson.get("replyCount").asText());
        setRetweetBtn(tweetJson.get("retweetCount").asText());
        setLikeBtn(tweetJson.get("likeCount").asText());
        setOwnerNameLbl(usersJson.get("firstName").asText() + " " + usersJson.get("lastName").asText());
        long createdAt = tweetJson.get("createdAt").asLong();
        setTimestampLbl(TimestampController.formatTimestamp(createdAt));
        // ... set other information on the controller

        try {
            URL url2 = new URL("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText() + "/profile-image");
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream inputStream = conn.getInputStream();
            Image image = new Image(inputStream);
            setAvatarView(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // update like count and style

        try {
            likeResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetJson.get("tweetId").asText() + "/likes", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode likesJson = null;
        try {
            likesJson = objectMapper.readTree(likeResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int likeCount = 0;
        for (JsonNode likeJson : likesJson) {
            likeCount++;
            if (likeJson.get("userId").asText().equals(JWTController.getSubjectFromJwt(JWTController.getJwtKey()))) {
                setTweetLiked();
            }
        }

        setLikeBtn(Integer.toString(likeCount));

        // update reply count and style
        // update retweet + quote count and style

        try {
            retweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetJson.get("tweetId").asText() + "/retweets", HttpMethod.GET, null, null);
            quoteResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetJson.get("tweetId").asText() + "/quotes", HttpMethod.GET, null, null);
            replyResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + tweetJson.get("tweetId").asText() + "/replies", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonNode repliesJson = null;
        JsonNode retweetsJson = null;
        JsonNode quotesJson = null;

        try {
            retweetsJson = objectMapper.readTree(retweetResponse.getBody());
            quotesJson = objectMapper.readTree(quoteResponse.getBody());
            repliesJson = objectMapper.readTree(replyResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int replyCount = 0;
        for (JsonNode replyJson : repliesJson) {
            replyCount++;
            if (replyJson.get("ownerId").asText().equals(JWTController.getSubjectFromJwt(JWTController.getJwtKey()))) {
                setTweetReplied();
            }
        }
        setReplyBtn(Integer.toString(replyCount));

        int retweetCount = 0;
        for (JsonNode retweetJson : retweetsJson) {
            retweetCount++;
            if (retweetJson.get("ownerId").asText().equals(JWTController.getSubjectFromJwt(JWTController.getJwtKey()))) {
                setTweetRetweeted();
            }
        }
        setRetweetBtn(Integer.toString(retweetCount));

        int quoteCount = 0;
        for (JsonNode quoteJson : quotesJson) {
            quoteCount++;
            if (quoteJson.get("ownerId").asText().equals(JWTController.getSubjectFromJwt(JWTController.getJwtKey()))) {
                setTweetQuoted();
            }
        }
        setQuoteBtn(Integer.toString(quoteCount));
    }

    public void addQuote(String quoteTweetId) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/quoteTweet.fxml"));
        Parent quoteTweetRoot = null;
        try {
            quoteTweetRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuoteTweetController quoteTweetController = fxmlLoader.getController();
        quoteTweetController.fillQuote(quoteTweetId);
        quoteTweetController.setMainController(mainController);
        contentContainer.getChildren().add(quoteTweetRoot);
    }

    public void addRetweetHeader(String username) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/retweetHeader.fxml"));
        Parent retweetHeaderRoot = null;
        try {
            retweetHeaderRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RetweetHeaderController retweetHeaderController = fxmlLoader.getController();
        retweetHeaderController.fillRetweetHeader(username);
        rootBp.setTop(retweetHeaderRoot);
    }

    public void addReply(String replyTweetId) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/tweet.fxml"));
        Parent replyTweetRoot = null;
        try {
            replyTweetRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TweetController replyTweetController = fxmlLoader.getController();
        replyTweetController.fillTweet(replyTweetId);
        replyTweetController.setMainController(mainController);
        rootVbox.getChildren().add(replyTweetRoot);
    }

    private void incrementLikeCount() {
        int likeCount = Integer.parseInt(likeBtn.getText());
        likeBtn.setText(String.valueOf(likeCount + 1));
    }

    private void decrementLikeCount() {
        int likeCount = Integer.parseInt(likeBtn.getText());
        likeBtn.setText(String.valueOf(likeCount - 1));
    }

    public void setTweetLiked() {
        likeBtn.setStyle("-fx-text-fill: #e0245e");
        likeIcon.setStyle("-fx-fill: #e0245e");
        setLiked(true);
    }

    public void setTweetUnliked() {
        likeBtn.setStyle("-fx-text-fill: #666");
        likeIcon.setStyle("-fx-fill: #666");
        setLiked(false);
    }

    private void setTweetReplied() {
        replyBtn.setStyle("-fx-text-fill: #1DA1F2");
        replyIcon.setStyle("-fx-fill: #1DA1F2");
        setReplied(true);
    }

    private void setTweetUnreplied() {
        replyBtn.setStyle("-fx-text-fill: #666");
        replyIcon.setStyle("-fx-fill: #666");
        setReplied(false);
    }

    public void setTweetRetweeted() {
        retweetBtn.setStyle("-fx-text-fill: #17bf63");
        retweetIcon.setStyle("-fx-fill: #17bf63");
        setRetweeted(true);
    }

    public void setTweetUnretweeted() {
        retweetBtn.setStyle("-fx-text-fill: #666");
        retweetIcon.setStyle("-fx-fill: #666");
        setRetweeted(false);
    }

    public void setTweetQuoted() {
        quoteBtn.setStyle("-fx-text-fill: #1DA1F2");
        quoteIcon.setStyle("-fx-fill: #1DA1F2");
        setQuoted(true);
    }

    public void setTweetUnquoted() {
        quoteBtn.setStyle("-fx-text-fill: #666");
        quoteIcon.setStyle("-fx-fill: #666");
        setQuoted(false);
    }
}
