package pl.rav.control;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javafx.stage.Stage;
import javafx.util.Duration;
import pl.rav.Game;
import pl.rav.game.Player;
import pl.rav.util.Graphics;
import pl.rav.util.Race;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static pl.rav.Game.scene;
import static pl.rav.Game.turn;
import static pl.rav.util.Const.*;

public class RootController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Label playerVsPlayer, turnNo;
    @FXML
    private Label nickLeft, raceLeft, leftMoves, winsLeft, losesLeft;
    @FXML
    private Label nickRight, raceRight, rightMoves, winsRight, losesRight;
    @FXML
    private ImageView avatarLeft, avatarRight;

    EventHandler<KeyEvent> eventHandlerFirstMove;
    EventHandler<KeyEvent> eventHandlerSecondMove;
    EventHandler<KeyEvent> eventHandlerFirstShot;
    EventHandler<KeyEvent> eventHandlerSecondShot;

    Graphics graphics;

    private Stage endStage;
    private Stage currentStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPlayerVsPlayer();
        setTurn();

        setAvatarLeft(avatarLeft, Game.playerGlobalFirst);
        setNick(nickLeft, Game.playerGlobalFirst);
        setRace(raceLeft, Game.playerGlobalFirst);
        setWinsAndLoses(winsLeft, Game.playerGlobalFirst, Game.playerGlobalFirst.getWins());
        setWinsAndLoses(losesLeft, Game.playerGlobalFirst, Game.playerGlobalFirst.getLoses());

        setAvatarLeft(avatarRight, Game.playerGlobalSecond);
        setNick(nickRight, Game.playerGlobalSecond);
        setRace(raceRight, Game.playerGlobalSecond);
        setWinsAndLoses(winsRight, Game.playerGlobalSecond, Game.playerGlobalSecond.getWins());
        setWinsAndLoses(losesRight, Game.playerGlobalSecond, Game.playerGlobalSecond.getLoses());

        graphics = new Graphics();

        ImageView imageViewPlayerFirst = placedWarriorOnTheBattlefield(
                Game.playerGlobalFirst.getAvatar().getPath(),
                BATTLEFIELD_START_X,
                BATTLEFIELD_START_Y
        );
        ImageView imageViewPlayerSecond = placedWarriorOnTheBattlefield(
                Game.playerGlobalSecond.getAvatar().getPath(),
                BATTLEFIELD_END_X - AVATAR_BATTLEFIELD_SIZE,
                BATTLEFIELD_END_Y - AVATAR_BATTLEFIELD_SIZE
        );

        Game.playerGlobalFirst.setOnTheBattlefield(imageViewPlayerFirst);
        Game.playerGlobalSecond.setOnTheBattlefield(imageViewPlayerSecond);

        ImageView bulletFirst = graphics.createGraphicsFromPath(BULLET_PATH, BULLET_WIDTH);
        eventHandlerFirstMove = warriorsMovement(Game.playerGlobalFirst, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D);
        eventHandlerFirstShot = warriorsShoot(Game.playerGlobalFirst, KeyCode.X, bulletFirst);

        imageViewPlayerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstMove);
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstShot);
        });

        ImageView bulletSecond = graphics.createGraphicsFromPath(BULLET_PATH, BULLET_WIDTH);
        eventHandlerSecondMove = warriorsMovement(Game.playerGlobalSecond, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
        eventHandlerSecondShot = warriorsShoot(Game.playerGlobalSecond, KeyCode.SHIFT, bulletSecond);

        imageViewPlayerSecond.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondMove);
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondShot);
        });
    }

    private void setPlayerVsPlayer() {
        playerVsPlayer.setText(Game.playerGlobalFirst.getNick() + " vs. " + Game.playerGlobalSecond.getNick());
    }

    private void setNick(Label leftOrRightSide, Player player) {
        leftOrRightSide.setText(player.getNick());
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    private void setTurn() {
        turnNo.setText("Turn no: " + Game.turn);
    }

    private void setAvatarLeft(ImageView where, Player player) {
        try (InputStream inputStream = new FileInputStream(player.getAvatar().getPath())) {
            Image image = new Image(inputStream);
            where.setImage(image);
            where.setVisible(true);
            where.setPreserveRatio(true);
            where.setFitHeight(AVATAR_MENU_SIZE);
        } catch (IOException e) {
            System.err.println("Cannot load image exception: " + e.getMessage());
        }
    }

    private void setRace(Label leftOrRightSide, Player player) {
        leftOrRightSide.setText(player.getRace().get());
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    private void setWinsAndLoses(Label leftOrRightSide, Player player, int points) {
        leftOrRightSide.setText("" + points);
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    //////////////////////////////////////////////// BATTLEFIELD - setup ///////////////////////////////////////////////

    public void goToTheBattle() {
        root.getChildren().add(Game.playerGlobalFirst.getOnTheBattlefield());
        root.getChildren().add(Game.playerGlobalSecond.getOnTheBattlefield());
    }

    private ImageView placedWarriorOnTheBattlefield(String avatarPath, double startX, double startY) {
        try (InputStream inputStream = new FileInputStream(avatarPath)) {
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setVisible(true);
            imageView.setFitHeight(AVATAR_BATTLEFIELD_SIZE);
            imageView.setFitWidth(AVATAR_BATTLEFIELD_SIZE);
            imageView.setX(startX);
            imageView.setY(startY);
            return imageView;
        } catch (IOException e) {
            System.err.println("Cannot load image exception: " + e.getMessage());
        }
        return null;
    }

    /////////////////////////////////////////////// MOVEMENT management ////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsMovement(
            Player player,
            KeyCode moveUp,
            KeyCode moveDown,
            KeyCode moveLeft,
            KeyCode moveRight) {
        ImageView player1 = player.getOnTheBattlefield();
        ImageView player2 = getOpponent(player).getOnTheBattlefield();

        EventHandler<KeyEvent> keyPressListener = keyEvent -> {
            if (keyEvent.getCode() == moveUp) {
                if (player1.getY() > BATTLEFIELD_START_Y) {
                    if ((int) player1.getX() == player2.getX() && (int) player1.getY() == (int) player2.getY() + AVATAR_BATTLEFIELD_SIZE) {
                    } else {
                        player1.setY(player1.getY() - AVATAR_BATTLEFIELD_SIZE);
                    }
                }
            }
            if (keyEvent.getCode() == moveLeft) {
                if ((int) player1.getX() > BATTLEFIELD_START_X) {
                    if ((int) player1.getX() == player2.getX() + AVATAR_BATTLEFIELD_SIZE && (int) player1.getY() == (int) player2.getY()) {
                        System.err.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.err.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                    } else {
                        player1.setX((int) player1.getX() - AVATAR_BATTLEFIELD_SIZE);
                    }
                }
            }
            if (keyEvent.getCode() == moveRight) {
                if (player1.getX() < BATTLEFIELD_END_X - AVATAR_BATTLEFIELD_SIZE) {
                    if ((int) player1.getX() == player2.getX() - AVATAR_BATTLEFIELD_SIZE && (int) player1.getY() == (int) player2.getY()) {
                        System.err.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.err.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                    } else {
                        player1.setX(player1.getX() + AVATAR_BATTLEFIELD_SIZE);
                    }
                }
            }
            if (keyEvent.getCode() == moveDown) {
                if (player1.getY() < BATTLEFIELD_END_Y - AVATAR_BATTLEFIELD_SIZE) {
                    if ((int) player1.getX() == player2.getX() && (int) player1.getY() == (int) player2.getY() - AVATAR_BATTLEFIELD_SIZE) {
                        System.err.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.err.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                    } else {
                        player1.setY(player1.getY() + AVATAR_BATTLEFIELD_SIZE);
                    }
                }
            }
        };
        return keyPressListener;
    }

    private Player getOpponent(Player player) {
        return player.equals(Game.playerGlobalFirst) ? Game.playerGlobalSecond : Game.playerGlobalFirst;
    }

    /////////////////////////////////////////////// SHOT management ////////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsShoot(
            Player player,
            KeyCode shot,
            ImageView bullet) {

        ImageView player1 = player.getOnTheBattlefield();
        Player opponent = getOpponent(player);
        ImageView player2 = opponent.getOnTheBattlefield();

        EventHandler<KeyEvent> keyShootListener = keyEvent -> {
            if (keyEvent.getCode() == shot && !root.getChildren().contains(bullet)) {

                mediaPlayer(SHOT_SOUND).play();

                double bulletFromX = player1.getX() + AVATAR_BATTLEFIELD_SIZE / 2;
                double bulletFromY = player1.getY() + AVATAR_BATTLEFIELD_SIZE / 2;
                double bulletToX = player2.getX() + AVATAR_BATTLEFIELD_SIZE / 2;
                double bulletToY = player2.getY() + AVATAR_BATTLEFIELD_SIZE / 2;

                ImageView explode = graphics.createGraphicsFromPath(EXPLODE_PATH, EXPLODE_WIDTH);

                root.getChildren().add(bullet);

                Path path = new Path();
                path.getElements().add(new MoveTo(bulletFromX, bulletFromY));    // From
                path.getElements().add(new LineTo(bulletToX, bulletToY));        // To

                TranslateTransition shotTransition = new TranslateTransition(Duration.millis(SHOT_DURATION), bullet);
                shotTransition.setFromX(bulletFromX);
                shotTransition.setFromY(bulletFromY);
                shotTransition.setToX(bulletToX);
                shotTransition.setToY(bulletToY);

                FadeTransition explodeTransition = new FadeTransition(Duration.millis(SHOT_DURATION / 2), explode);
                explodeTransition.setFromValue(1.0f);
                explodeTransition.setToValue(0f);
                explodeTransition.setCycleCount(1);

                bullet.setLayoutX(bulletFromX);
                bullet.setLayoutY(bulletFromY);

                double deltaX = (bulletFromX - bulletToX) / SHOT_DURATION;
                double deltaY = (bulletFromY - bulletToY) / SHOT_DURATION;

                double deltaAllX = (bulletFromX - bulletToX);
                double deltaAllY = (bulletFromY - bulletToY);
                double distance = Math.sqrt(Math.pow(deltaAllX, 2) + Math.pow(deltaAllY, 2));
                int oneCycleTime = distance <= SHOT_DURATION ? 1 : (int) (distance / SHOT_DURATION);

                Timeline timeline = new Timeline();

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(oneCycleTime),
                                actionEvent -> {

                                    bullet.setLayoutX(bullet.getLayoutX() - deltaX);
                                    bullet.setLayoutY(bullet.getLayoutY() - deltaY);

                                    if (isOpponentHit(bullet, player2)) {
                                        if (opponent.getHealth() == 1) {
                                            soundEffectOnDeath(opponent);
                                            opponent.reduceHealth();
                                            root.getChildren().remove(bullet);

                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstMove);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondMove);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstShot);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondShot);

                                            player.increaseWins();
                                            opponent.increaseLoses();
                                            Game.incrementTurn();

                                            timeline.stop();
                                            player.setJustWon(true);
                                            loadEndWindow();

                                            return;
                                        }

                                        soundEffectOnHit(opponent);
                                        opponent.reduceHealth();

                                        root.getChildren().remove(bullet);
                                        explode.setLayoutX(bullet.getLayoutX() - 12);
                                        explode.setLayoutY(bullet.getLayoutY() - 12);
                                        root.getChildren().add(explode);
                                        explodeTransition.play();
                                        timeline.stop();
                                    }
                                })
                );

                timeline.setOnFinished(event -> {
                    root.getChildren().remove(bullet);
                    explode.setLayoutX(bullet.getLayoutX());
                    explode.setLayoutY(bullet.getLayoutY());
                    explodeTransition.play();
                    if (root.getChildren().contains(explode)) {
                        root.getChildren().remove(explode);
                    }
                });

                timeline.setCycleCount((int) SHOT_DURATION);
                timeline.play();
            }
        };

        return keyShootListener;
    }

    private boolean isOpponentHit(ImageView bullet, ImageView player2) {
        return bullet.getLayoutX() <= player2.getX() + AVATAR_BATTLEFIELD_SIZE && bullet.getLayoutX() >= player2.getX() &&
                bullet.getLayoutY() <= player2.getY() + AVATAR_BATTLEFIELD_SIZE && bullet.getLayoutY() >= player2.getY();
    }

    private boolean isMonster(Player player) {
        return player.getRace().equals(Race.MONSTERS);
    }

    private void soundEffectOnHit(Player player) {
        if (isMonster(player)) {
            mediaPlayer(HIT_MONSTER_SOUND).play();
        } else {
            mediaPlayer(HIT_HUMAN_SOUND).play();
        }
    }

    private void soundEffectOnDeath(Player player) {
        if (isMonster(player)) {
            mediaPlayer(DEATH_MONSTER_SOUND).play();
        } else {
            mediaPlayer(DEATH_HUMAN_SOUND).play();
        }
    }

    private void loadEndWindow() {
        currentStage = ((Stage) root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("end.fxml"));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        endStage = new Stage();
        endStage.setTitle("FIGHT OVER - STATISTICS");
        endStage.setScene(scene);
        endStage.setWidth(WELCOME_WIDTH);
        endStage.setHeight(WELCOME_HEIGHT);
        endStage.show();

        currentStage.hide();
    }

    private MediaPlayer mediaPlayer(String path) {
        Media media = new Media(new File(path).toURI().toString());
        return new MediaPlayer(media);
    }

}


