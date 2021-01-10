package pl.rav.control;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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


import javafx.util.Duration;
import pl.rav.Game;
import pl.rav.game.Player;
import pl.rav.util.Avatar;
import pl.rav.util.Graphics;
import pl.rav.util.Race;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static pl.rav.util.Const.*;

public class RootController implements Initializable {

    // SETUP - phase
    @FXML
    private BorderPane root;
    @FXML
    private Label playerVsPlayer;
    @FXML
    private Label nickLeft, raceLeft, leftMoves, scoreLeft;
    @FXML
    private Label nickRight, raceRight, rightMoves, scoreRight;
    @FXML
    private ImageView avatarLeft, avatarRight;

    // BATTLE - phase
    @FXML
    private GridPane battlefield;

//    private Label whoWon;

    private boolean isEnd;

    //    private ImageView imageViewPlayerFirst;
//    private ImageView imageViewPlayerSecond;
    EventHandler<KeyEvent> eventHandlerFirstMove;
    EventHandler<KeyEvent> eventHandlerSecondMove;
    EventHandler<KeyEvent> eventHandlerFirstShot;
    EventHandler<KeyEvent> eventHandlerSecondShot;

    Graphics graphics;

    private List<ImageView> rocketsFirstPlayer;

    // SETUP - adding players parameters on LEFT and RIGHT and info on TOP

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

// TODO: !!! - wyłączone na czas setup battlefield - !!!!

//        setPlayerVsPlayer();
//
//        setAvatarLeft(avatarLeft, Game.playerGlobalFirst);
//        setNick(nickLeft, Game.playerGlobalFirst);
//        setRace(raceLeft, Game.playerGlobalFirst);
//        setScore(scoreLeft, Game.playerGlobalFirst, INITIAL_POINTS);
//        setMoves(leftMoves, "0");
//
//        setAvatarLeft(avatarRight, Game.playerGlobalSecond);
//        setNick(nickRight, Game.playerGlobalSecond);
//        setRace(raceRight, Game.playerGlobalSecond);
//        setScore(scoreRight, Game.playerGlobalSecond, INITIAL_POINTS);
//        setMoves(rightMoves, "0");

// TODO: !!! - wyłączone na czas setup battlefield - !!!!

        graphics = new Graphics();

        Game.playerGlobalFirst = new Player("Ksawery", Race.HUMANS, Avatar.HEAVY_GUY);        // TODO: !!! - wyłączone TYLKO na czas setup battlefield - !!!!
        Game.playerGlobalSecond = new Player("Rafal", Race.MONSTERS, Avatar.MEDIUM_MONSTER);  // TODO: !!! - wyłączone TYLKO na czas setup battlefield - !!!!

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
        eventHandlerFirstShot  = warriorsShoot(Game.playerGlobalFirst, KeyCode.X, bulletFirst);

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

    private void setAvatarLeft(ImageView where, Player player) {
        try (InputStream inputStream = new FileInputStream(player.getAvatar().getPath())) {
            Image image = new Image(inputStream);
            where.setImage(image);
            where.setVisible(true);
            where.setPreserveRatio(true);
            where.setFitHeight(AVATAR_MENU_SIZE);
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

    private void setMoves(Label leftOrRightSide, String moves) {
        leftOrRightSide.setText(moves);
        leftOrRightSide.setStyle("-fx-font-weight: bold");
    }

    //////////////////////////////////////////////// BATTLEFIELD - setup //////////////////////////////////////////////////////////////

    public void goToTheBattle() {
        root.getChildren().add(Game.playerGlobalFirst.getOnTheBattlefield());
        root.getChildren().add(Game.playerGlobalSecond.getOnTheBattlefield());


//        System.out.println("x: " + imageViewPlayerFirst.getX());
//        System.out.println("y: " + imageViewPlayerFirst.getY());
//        System.out.println("height: " + imageViewPlayerFirst.getFitHeight());
//        System.out.println("width:  " + imageViewPlayerFirst.getFitWidth());
//        System.out.println("BATTLEFIELD_START_Y:  " + BATTLEFIELD_START_Y);
//        System.out.println("BATTLEFIELD_END_Y:  " + BATTLEFIELD_END_Y);
    }

    private ImageView placedWarriorOnTheBattlefield(String avatarPath, double startX, double startY) {

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

    /////////////////////////////////////////////// MOVEMENT /////////////////////////////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsMovement(Player player, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {

        ImageView player1 = player.getOnTheBattlefield();
        ImageView player2 = getOpponent(player).getOnTheBattlefield();

        EventHandler<KeyEvent> keyPressListener = keyEvent -> {
            if (keyEvent.getCode() == moveUp) {
                if (player1.getY() > BATTLEFIELD_START_Y) {
                    if ((int) player1.getX() == player2.getX() && (int) player1.getY() == (int) player2.getY() + AVATAR_BATTLEFIELD_SIZE) {
                        System.err.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.err.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                    } else {
                        player1.setY(player1.getY() - AVATAR_BATTLEFIELD_SIZE);

                        System.out.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.out.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                        System.out.println();
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
                        System.out.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.out.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                        System.out.println();
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
                        System.out.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.out.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                        System.out.println();
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
//                        incrementMoves(leftMoves);                                     TODO: !!! - wyłączone na czas setup battlefield - !!!!
                        System.out.println("coordinates player1: x=" + player1.getX() + ", y=" + player1.getY());
                        System.out.println("coordinates player2: x=" + player2.getX() + ", y=" + player2.getY());
                        System.out.println();
                    }
                }
            }
        };
        return keyPressListener;
    }

    private Player getOpponent(Player player) {
        return player.equals(Game.playerGlobalFirst) ? Game.playerGlobalSecond : Game.playerGlobalFirst;
    }

    /////////////////////////////////////////////// SHOT //////////////////////////////////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsShoot(Player player, KeyCode shot, ImageView bullet) {

        ImageView player1 = player.getOnTheBattlefield();
        Player opponent = getOpponent(player);
        ImageView player2 = opponent.getOnTheBattlefield();

        EventHandler<KeyEvent> keyShootListener = keyEvent -> {
            if (keyEvent.getCode() == shot && !root.getChildren().contains(bullet)) {

                System.out.println(player.getNick() + " SHOT towards " + opponent.getNick());

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
//                System.out.println(RED + "distance: " + distance + RESET);
//                System.out.println(RED + "oneCycleTime: " + oneCycleTime + RESET);

                Timeline timeline = new Timeline();
//                                    double deltaX = (bulletFromX - bulletToX) / SHOT_DURATION;
//                                    double deltaY = (bulletFromY - bulletToY) / SHOT_DURATION;
                timeline.getKeyFrames().add(

                        new KeyFrame(
                                Duration.millis(oneCycleTime),
                                actionEvent -> {

                                    bullet.setLayoutX(bullet.getLayoutX() - deltaX);
                                    bullet.setLayoutY(bullet.getLayoutY() - deltaY);
//                                        System.out.println("bullet position: " + bullet.getLayoutX() + ", " + bullet.getLayoutY());
//                                        System.out.println("player2 position: " + player2.getX() + ", " + player2.getY());
//                                        System.out.println("SHOOTING AREA X: " + (player2.getX() + AVATAR_BATTLEFIELD_SIZE - 15) + ", " + (player2.getX() + 15));
//                                        System.out.println("SHOOTING AREA Y: " + (player2.getY() + AVATAR_BATTLEFIELD_SIZE - 15) + ", " + (player2.getY() + 15));
//                                        System.out.println(BLUE + "DELTA: " + deltaX + ", " + deltaY + RESET);
//                                        if (bullet.getLayoutX() <= player2.getX() + AVATAR_BATTLEFIELD_SIZE && bullet.getLayoutX() >= player2.getX() && bullet.getLayoutY() <= player2.getY() + AVATAR_BATTLEFIELD_SIZE && bullet.getLayoutY() >= player2.getY()) {

                                    if (isOpponentHit(bullet, player2)) {

                                        ///////////////////////////////////////////// START - do metody aktualizacji życia
                                        if (opponent.getHealth()  == 1){
                                            soundEffectOnDeath(opponent);
                                            opponent.reduceHealth();
                                            System.out.println(opponent.getNick() + " health = " + opponent.getHealth());
                                            System.out.println(BLUE + player.getNick() + " has won the game !!!  .... and " + opponent.getNick() + " lose" + RESET);
                                            root.getChildren().remove(bullet);

                                            
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstMove);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondMove);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerFirstShot);
                                            root.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerSecondShot);
//                                            root.getChildren().remove(player.getOnTheBattlefield());
//                                            root.getChildren().remove(opponent.getOnTheBattlefield());


//                                            Rectangle rectangle = new Rectangle(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT);
//                                            rectangle.setFill(Color.BLUE);
//                                            rectangle.setLayoutX(200);
//                                            rectangle.setLayoutY(100);
////                                            root.getChildren().add(rectangle);
//
//                                            Label label = new Label(player.getNick() + " has won the game... CONGRATULATIONS !!!");
//                                            label.setTextFill(Color.WHITE);
//                                            label.setLayoutX(200);
//                                            label.setLayoutY(100);
//                                            root.getChildren().add(label);

//                                            Label whoWon = new Label(player.getNick() + " has won the game !!! .... CONGRATULATIONS !!!");
//                                            whoWon.setTextFill(Color.WHITE);
//                                            root.getChildren().add(whoWon);
//                                            GridPane.setValignment(whoWon, VPos.CENTER);
//                                            GridPane.setHalignment(whoWon, HPos.CENTER);
//                                            whoWon.setAlignment(Pos.CENTER);
//                                            whoWon.setVisible(true);

                                            player.increaseWins();
                                            opponent.increaseLoses();

                                            System.out.println("STATISTICS - " + player.getNick() + ": has " +player.getWins() +" wins and " + player.getLoses() + " loses");
                                            System.out.println("STATISTICS - " + opponent.getNick() + ": has " +opponent.getWins() +" wins and " + opponent.getLoses() + " loses");

                                            timeline.stop();

                                            try {
                                                player.setJustWon(true);
                                                Game.setRoot("end",WELCOME_WIDTH+15, WELCOME_HEIGHT+35);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            return;
                                        }

                                        System.out.println(RED + opponent.getNick() + " was hit... ała !!! " + RESET);
                                        soundEffectOnHit(opponent);
                                        opponent.reduceHealth();
                                        System.out.println(opponent.getNick() + " health = " + opponent.getHealth());

//                                                removeNodeIfExists(player.getOnTheBattlefield());
//                                                removeNodeIfExists(opponent.getOnTheBattlefield());
//                                                removeNodeIfExists(bullet);
//                                                removeNodeIfExists(explode);
//                                                root.getChildren().remove(player.getOnTheBattlefield());
//                                                root.getChildren().remove(opponent.getOnTheBattlefield());
//                                                player.getOnTheBattlefield().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerS);
//                                                opponent.getOnTheBattlefield().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerF);
//                                                opponent.getOnTheBattlefield().removeEventFilter(KeyEvent.KEY_PRESSED, eventHandlerS);

//                                                player.getOnTheBattlefield().sceneProperty().get;

//                                                imageViewPlayerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
//                                                    newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(Game.playerGlobalFirst, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));
//                                                    newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsShoot(Game.playerGlobalFirst, KeyCode.X, bulletFirst));
//                                                });

//                                                player.getOnTheBattlefield().removeEventFilter(KeyEvent.KEY_PRESSED,this);
//                                                Game.setRoot("welcome",WELCOME_WIDTH+15, WELCOME_HEIGHT+35);

                                        //////////////////////////////////////////// END - do metody aktualizacji życia

                                        root.getChildren().remove(bullet);
                                        explode.setLayoutX(bullet.getLayoutX() - 12);
                                        explode.setLayoutY(bullet.getLayoutY() - 12);
                                        root.getChildren().add(explode);
                                        explodeTransition.play();
//                                            root.getChildren().remove(explode);
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

    private void removeNodeIfExists(Node node) {
        if (root.getChildren().contains(node)) {
            root.getChildren().remove(node);

        }
    }

    private boolean isMonster(Player player) {
        return player.getRace().equals(Race.MONSTERS);
    }

    private void soundEffectOnHit(Player player) {
        if (isMonster(player)) {
            mediaPlayer("src/main/resources/pl/rav/graphics/avatars/monsters/hitMonster.mp3").play();
        } else {
            mediaPlayer("src/main/resources/pl/rav/graphics/avatars/humans/hitHuman.mp3").play();
        }
    }

    private void soundEffectOnDeath(Player player) {
        if (isMonster(player)) {
            mediaPlayer("src/main/resources/pl/rav/graphics/avatars/monsters/deathMonster.mp3").play();
        } else {
            mediaPlayer("src/main/resources/pl/rav/graphics/avatars/humans/deathHuman.mp3").play();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void incrementMoves(Label playerMoves) {
        int currentMoves = Integer.parseInt(playerMoves.getText());
        String incrementedMove = String.valueOf(++currentMoves);
        playerMoves.setText(incrementedMove);
    }

    private boolean areNotPlayersOverlapping(ImageView player1, ImageView player2) {
//        if (player1.getX() != player2.getX() - AVATAR_BATTLEFIELD_SIZE && player1.getY() != player2.getY() - AVATAR_BATTLEFIELD_SIZE) {
        if (player1.getX() < player2.getX() - AVATAR_BATTLEFIELD_SIZE) {
            return true;
        }

        return false;
    }


    /////////////////////////////////////////////////////

    private MediaPlayer mediaPlayer(String path) {
        Media media = new Media(new File(path).toURI().toString());
        return new MediaPlayer(media);
    }

//    private ImageView createGraphicsFromPath(String path, int width) {
//        try (InputStream inputStream = new FileInputStream(path)) {
//            return createGraphicsFromInputStream(inputStream, width);
//        } catch (IOException e) {
//            System.err.println("Cannot load image exception: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private ImageView createGraphicsFromInputStream(InputStream inputStream, int width) {
//        Image image = new Image(inputStream);
//        ImageView imageView = new ImageView(image);
//        imageView.setVisible(true);
//        imageView.setPreserveRatio(true);
//        imageView.setFitWidth(width);
//        return imageView;
//    }

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


