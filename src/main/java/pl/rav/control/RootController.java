package pl.rav.control;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

    private ImageView playerFirst;
    private ImageView playerSecond;

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

        playerFirst = placedWarriorOnTheBattlefield(
                Game.playerGlobalFirst.getAvatar().getPath(),
//                "src/main/resources/pl/rav/graphics/avatars/humans/humanAvatar03.png",
                BATTLEFIELD_START_X,
                BATTLEFIELD_START_Y
        );
        playerSecond = placedWarriorOnTheBattlefield(
                Game.playerGlobalSecond.getAvatar().getPath(),
//                "src/main/resources/pl/rav/graphics/avatars/monsters/monsterAvatar03.png",
                BATTLEFIELD_END_X-AVATAR_BATTLEFIELD_SIZE,
                BATTLEFIELD_END_Y-AVATAR_BATTLEFIELD_SIZE
        );

        playerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(playerFirst, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));
        });

        playerSecond.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(playerSecond, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT));
        });

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
            where.setFitHeight(AVATAR_MENU_HEIGHT);
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
        root.getChildren().add(playerSecond);


        System.out.println("x: " + playerFirst.getX());
        System.out.println("y: " + playerFirst.getY());
        System.out.println("height: " + playerFirst.getFitHeight());
        System.out.println("width:  " + playerFirst.getFitWidth());
        System.out.println("BATTLEFIELD_END_X:  " + BATTLEFIELD_END_X);
        System.out.println("BATTLEFIELD_END_y:  " + BATTLEFIELD_END_Y);
    }

    private ImageView placedWarriorOnTheBattlefield(String avatarPath, int startX, int startY) {

        try (InputStream inputStream = new FileInputStream(avatarPath)) {
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setVisible(true);
//            imageView.setPreserveRatio(true); // utrzymuje oryginalny współczynnik przy rskalowaniu i nie da się zrobić kwadratu 50 x 50 : )
            imageView.setFitHeight(AVATAR_BATTLEFIELD_SIZE);
            imageView.setFitWidth(AVATAR_BATTLEFIELD_SIZE);
//            imageView.setLayoutX(BATTLEFIELD_START_X);
//            imageView.setLayoutY(BATTLEFIELD_START_Y);
            imageView.setX(startX);
            imageView.setY(startY);
            return imageView;
        } catch (IOException e) {
            System.err.println("Cannot load image exception (Class RootController): " + e.getMessage());
        }

        return null;

    }

    private EventHandler<KeyEvent> warriorsMovement(ImageView player, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {

        EventHandler<KeyEvent> keyPressListener = keyEvent -> {
            if (keyEvent.getCode() == moveUp) {
                if (player.getY() > BATTLEFIELD_START_Y) {
                    player.setY(player.getY() - AVATAR_BATTLEFIELD_SIZE);
                    System.out.println("W pressed -> y: " + player.getY());
                }
            }
            if (keyEvent.getCode() == moveLeft) {
                if (player.getX() > BATTLEFIELD_START_X) {
                    player.setX(player.getX() - AVATAR_BATTLEFIELD_SIZE);
                    System.out.println("A pressed -> x: " + player.getX());
                }
            }
            if (keyEvent.getCode() == moveRight) {
                if (player.getX() < BATTLEFIELD_END_X - AVATAR_BATTLEFIELD_SIZE) {
                    player.setX(player.getX() + AVATAR_BATTLEFIELD_SIZE);
                    System.out.println("D pressed -> x: " + player.getX());
                }
            }
            if (keyEvent.getCode() == moveDown) {
                if (player.getY() < BATTLEFIELD_END_Y - AVATAR_BATTLEFIELD_SIZE) {
                    System.out.println("S pressed -> y: " + player.getY());
                    player.setY(player.getY() + AVATAR_BATTLEFIELD_SIZE);
                }
            }
        };
        return keyPressListener;
    }



//    EventHandler<KeyEvent> keyPressListener = keyEvent -> {
//        switch (keyEvent.getCode()) {
//            case W:
//                if (playerFirst.getY() > BATTLEFIELD_START_Y) {
//                    playerFirst.setY(playerFirst.getY() - AVATAR_BATTLEFIELD_SIZE);
//                    System.out.println("W pressed -> y: " + playerFirst.getY());
//                }
//                break;
//            case A:
//                if (playerFirst.getX() > BATTLEFIELD_START_X) {
//                    playerFirst.setX(playerFirst.getX() - AVATAR_BATTLEFIELD_SIZE);
//                    System.out.println("A pressed -> x: " + playerFirst.getX());
//                }
//                break;
//            case D:
//                if (playerFirst.getX() < BATTLEFIELD_END_X - AVATAR_BATTLEFIELD_SIZE) {
//                    playerFirst.setX(playerFirst.getX() + AVATAR_BATTLEFIELD_SIZE);
//                    System.out.println("D pressed -> x: " + playerFirst.getX());
//                }
//                break;
//            case S:
//                if (playerFirst.getY() < BATTLEFIELD_END_Y - AVATAR_BATTLEFIELD_SIZE) {
//                    System.out.println("S pressed -> y: " + playerFirst.getY());
//                    playerFirst.setY(playerFirst.getY() + AVATAR_BATTLEFIELD_SIZE);
//                }
//                break;
//            default:
//                break;
//        }
//    };

}


