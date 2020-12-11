package pl.rav.control;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pl.rav.Game;
import pl.rav.game.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static pl.rav.util.Const.*;

public class RootController implements Initializable {

    // SETUP - phase
    @FXML
    private BorderPane root;
    @FXML
    private Label playerVsPlayer;
    @FXML
    private Label nickLeft, raceLeft, scoreLeft;
    @FXML
    private Label nickRight, raceRight, scoreRight;
    @FXML
    private ImageView avatarLeft, avatarRight;

    // BATTLE - phase
    @FXML
    private GridPane battlefield;

    private ImageView playerFirst = createPlayerWarriorImage();

    // SETUP - adding players parameters on LEFT and RIGHT and info on TOP

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // !!! - wyłączone na czas setup battlefield - !!!!
        setPlayerVsPlayer();

        setAvatarLeft(avatarLeft, Game.playerGlobalFirst);
        setNick(nickLeft, Game.playerGlobalFirst);
        setRace(raceLeft, Game.playerGlobalFirst);
        setScore(scoreLeft, Game.playerGlobalFirst, INITIAL_POINTS);

        setAvatarLeft(avatarRight, Game.playerGlobalSecond);
        setNick(nickRight, Game.playerGlobalSecond);
        setRace(raceRight, Game.playerGlobalSecond);
        setScore(scoreRight, Game.playerGlobalSecond, INITIAL_POINTS);
        // !!! - wyłączone na czas setup battlefield - !!!!

        playerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressListener);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressListener);
            }
        });

//        EventHandler<KeyEvent> keyPressListener = w -> {
//            System.out.println("PRESSED");
//            playerFirst.setY(playerFirst.getY() + 100);
//        };

//        EventHandler<KeyEvent> keyPressListener = keyEvent -> {
//                    if (keyEvent.getCode() == KeyCode.S) {
//                        playerFirst.setY(playerFirst.getY() + 100);
//                    }
//                };

    }

    private void setPlayerVsPlayer() {
        playerVsPlayer.setText(Game.playerGlobalFirst.getNick() + " vs. " + Game.playerGlobalSecond.getNick());
    }

    private void setNick(Label leftOrRightSide, Player player) {
        leftOrRightSide.setText(player.getNick());
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    private void setAvatarLeft(ImageView where, Player player) {
        try (InputStream inputStream = new FileInputStream(player.getAvatar().getPath())) {
            Image image = new Image(inputStream);
            where.setImage(image);
            where.setVisible(true);
            where.setPreserveRatio(true);
            where.setFitHeight(AVATAR_HEIGHT);
        } catch (IOException e) {
            System.err.println("Cannot load image exception (Class RootController): " + e.getMessage());
        }
    }

    private void setRace(Label leftOrRightSide, Player player) {
        leftOrRightSide.setText(player.getRace().get());
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    private void setScore(Label leftOrRightSide, Player player, String points) {
        leftOrRightSide.setText(points);
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////   BATTLE - center window  ///////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void goToTheBattle() {
        root.getChildren().add(playerFirst);
    }

    private ImageView createPlayerWarriorImage() {

        try (InputStream inputStream = new FileInputStream("src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar01.png")) {
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setVisible(true);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(AVATAR_HEIGHT);
//            imageView.setLayoutX(BATTLEFIELD_START_X);
//            imageView.setLayoutY(BATTLEFIELD_START_Y);
            imageView.setX(BATTLEFIELD_START_X);
            imageView.setY(BATTLEFIELD_START_Y);
            return imageView;
        } catch (IOException e) {
            System.err.println("Cannot load image exception (Class RootController): " + e.getMessage());
        }

        return null;

    }

//    EventHandler<KeyEvent> keyPressListener = w -> {
//        System.out.println("PRESSED");
//        playerFirst.setY(playerFirst.getY() + 100);
//    };

    EventHandler<KeyEvent> keyPressListener = keyEvent -> {
        switch (keyEvent.getCode()) {
            case W:
                if (playerFirst.getY() > BATTLEFIELD_START_Y) {
                    playerFirst.setY(playerFirst.getY() - AVATAR_HEIGHT);
                    System.out.println("W pressed -> y: " + playerFirst.getY());
                }
                break;
            case A:
                if (playerFirst.getX() > BATTLEFIELD_START_X) {
                    playerFirst.setX(playerFirst.getX() - AVATAR_HEIGHT);
                    System.out.println("A pressed -> x: " + playerFirst.getX());
                }
                break;
            case D:
                if (playerFirst.getX() < BATTLEFIELD_END_X - AVATAR_HEIGHT) {
                    playerFirst.setX(playerFirst.getX() + AVATAR_HEIGHT);
                    System.out.println("D pressed -> x: " + playerFirst.getX());
                }
                break;
            case S:
                if (playerFirst.getY() < BATTLEFIELD_END_Y - AVATAR_HEIGHT) {
                    System.out.println("S pressed -> y: " + playerFirst.getY());
                    playerFirst.setY(playerFirst.getY() + AVATAR_HEIGHT);
                }
                break;
            default:
                break;
        }
    };

}