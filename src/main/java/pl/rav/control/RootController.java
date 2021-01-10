package pl.rav.control;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javafx.util.Duration;
import pl.rav.Game;
import pl.rav.game.Player;

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

    private ImageView playerFirst;
    private ImageView playerSecond;

    private List<ImageView> rocketsFirstPlayer;

    // SETUP - adding players parameters on LEFT and RIGHT and info on TOP

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

// TODO: !!! - wyłączone na czas setup battlefield - !!!!
//
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

        playerFirst = placedWarriorOnTheBattlefield(
//                Game.playerGlobalFirst.getAvatar().getPath(),             // TODO: !!! - wyłączone na czas setup battlefield - !!!!
                "src/main/resources/pl/rav/graphics/avatars/humans/humanAvatar03.png",
                BATTLEFIELD_START_X,
                BATTLEFIELD_START_Y
        );
        playerSecond = placedWarriorOnTheBattlefield(
//                Game.playerGlobalSecond.getAvatar().getPath(),            // TODO: !!! - wyłączone na czas setup battlefield - !!!!
                "src/main/resources/pl/rav/graphics/avatars/monsters/monsterAvatar03.png",
                BATTLEFIELD_END_X - AVATAR_BATTLEFIELD_SIZE,
                BATTLEFIELD_END_Y - AVATAR_BATTLEFIELD_SIZE
//                400,
//                300
        );

//        playerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
//            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(playerFirst, playerSecond, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                playerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsShoot(playerFirst, playerSecond, KeyCode.X)));
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


        Timeline timelineFirst = new Timeline(new KeyFrame(Duration.millis(SHOT_DURATION)));
        playerFirst.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(playerFirst, playerSecond, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D));
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsShoot(playerFirst, playerSecond, KeyCode.X, timelineFirst));
//            newScene.addEventFilter(KeyEvent.KEY_PRESSED, eventEventHandler);
        });

        Timeline timelineSecond = new Timeline(new KeyFrame(Duration.millis(SHOT_DURATION)));

        playerSecond.sceneProperty().addListener((obs, oldScene, newScene) -> {
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsMovement(playerSecond, playerFirst, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT));
            newScene.addEventFilter(KeyEvent.KEY_PRESSED, warriorsShoot(playerSecond, playerFirst, KeyCode.SHIFT, timelineSecond));
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

    private void setMoves(Label leftOrRightSide, String moves) {
        leftOrRightSide.setText(moves);
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
        System.out.println("BATTLEFIELD_START_Y:  " + BATTLEFIELD_START_Y);
        System.out.println("BATTLEFIELD_END_Y:  " + BATTLEFIELD_END_Y);
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

    /////////////////////////////////////////////// MOVEMENT /////////////////////////////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsMovement(ImageView player1, ImageView player2, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {

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

    /////////////////////////////////////////////// SHOT //////////////////////////////////////////////////////////////////////////////

    private EventHandler<KeyEvent> warriorsShoot(
            ImageView player1,
            ImageView player2,
            KeyCode shot,
            Timeline timeline
    ) {
        EventHandler<KeyEvent> keyShootListener = keyEvent -> {

            System.out.println("UP: " + timeline.getStatus());

            if (keyEvent.getCode() == shot && timeline.getStatus() != Animation.Status.RUNNING) {
//            if (keyEvent.getCode() == shot) {

                soundEffect("src/main/resources/pl/rav/graphics/bullets/bulletSound01.mp3").play();

                String pathBullet = "src/main/resources/pl/rav/graphics/bullets/bullet01.png";
                String pathExplode = "src/main/resources/pl/rav/graphics/explode/explode02.png";

                int bulletFromX = (int) player1.getX() + AVATAR_BATTLEFIELD_SIZE / 2;
                int bulletFromY = (int) player1.getY() + AVATAR_BATTLEFIELD_SIZE / 2;
                int bulletToX = (int) player2.getX();// + AVATAR_BATTLEFIELD_SIZE / 2;
                int bulletToY = (int) player2.getY();// + AVATAR_BATTLEFIELD_SIZE / 2;

                ImageView bullet = createGraphicsFromPath(pathBullet, BULLET_WIDTH);
                ImageView explode = createGraphicsFromPath(pathExplode, EXPLODE_WIDTH);


//                try (InputStream inputStreamBullet = new FileInputStream(pathBullet); InputStream inputStreamExplode = new FileInputStream(pathExplode)) {

//                    ImageView bullet = createGraphics(inputStreamBullet, BULLET_WIDTH);
                    root.getChildren().add(bullet);

//                    ImageView explode = createGraphicsFromInputStream(inputStreamExplode, EXPLODE_WIDTH);


                    Path path = new Path();
                    path.getElements().add(new MoveTo(bulletFromX, bulletFromY));    // From
                    path.getElements().add(new LineTo(bulletToX, bulletToY));        // To

                    TranslateTransition shotTransition = new TranslateTransition(Duration.millis(SHOT_DURATION), bullet);
                    shotTransition.setFromX(bulletFromX);
                    shotTransition.setFromY(bulletFromY);
                    shotTransition.setToX(bulletToX);
                    shotTransition.setToY(bulletToY);


                    explode.setLayoutX(bulletToX);
                    explode.setLayoutY(bulletToY);
                    FadeTransition explodeTransition = new FadeTransition(Duration.millis(SHOT_DURATION / 2), explode);
                    explodeTransition.setFromValue(1.0f);
                    explodeTransition.setToValue(0f);
                    explodeTransition.setCycleCount(1);


//                    Timeline timeline = new Timeline(
//                            new KeyFrame(Duration.millis(0), new KeyValue(bullet.translateXProperty(), bulletFromX), new KeyValue(bullet.translateYProperty(), bulletFromY)),
//                            new KeyFrame(Duration.millis(2000), new KeyValue(bullet.translateXProperty(), bulletToX), new KeyValue(bullet.translateYProperty(), bulletToY)),
//                            new KeyFrame(Duration.millis(3000))
//                    );


                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(0), new KeyValue(bullet.translateXProperty(), bulletFromX), new KeyValue(bullet.translateYProperty(), bulletFromY)),
                            new KeyFrame(Duration.millis(SHOT_DURATION), new KeyValue(bullet.translateXProperty(), bulletToX), new KeyValue(bullet.translateYProperty(), bulletToY))
//                            new KeyFrame(Duration.millis(3000))
                    );


                    BooleanBinding hit = Bindings.createBooleanBinding(() -> {
                        Point2D targetLocation = player2.localToParent(player2.getX(), player2.getY());
                        Point2D projectileLocation = bullet.localToParent(bullet.getX(), bullet.getY());
                        System.out.println();
                        System.out.println("player2.getX(): " + targetLocation.getX());
                        System.out.println("player2.getY(): " + targetLocation.getY());
                        System.out.println("bullet.getX(): " + projectileLocation.getX());
                        System.out.println("bullet.getY(): " + projectileLocation.getY());
                        System.out.println("distance: " + targetLocation.distance(projectileLocation));

                        System.out.println();
                        return (targetLocation.distance(projectileLocation) <= 50);
                    }, bullet.translateXProperty(), bullet.translateYProperty());
//
//
                    hit.addListener((obs, wasHit, isNowHit) -> {
//                        System.out.println();
//                        System.out.println("player2.getX(): " + (int)targetLocation.getX());
//                        System.out.println("player2.getY(): " + (int)targetLocation.getY());
//                        System.out.println("bullet.getX(): " + (int)projectileLocation.getX());
//                        System.out.println("bullet.getY(): " + (int)projectileLocation.getY());
//                        System.out.println();
                        if (isNowHit) {
                            System.out.println("Hit!!!!");
//                            root.getChildren().remove(bullet);
//                            root.getChildren().remove(target);
//                            targetMotion.stop();
//                            shot.stop();
                        }
                    });

//                    timeline.getKeyFrames().addListener(
//                            (InvalidationListener) event -> {
//                                if (player2.getX() - bullet.getX() < 50 && player2.getY() - bullet.getY() < 50){
//                                    System.out.println("HIT !!!");
//                                }
//                            }
//                    );


                    KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), new KeyValue(bullet.translateXProperty(), bulletFromX), new KeyValue(bullet.translateYProperty(), bulletFromY));
                    KeyFrame keyFrame3 = new KeyFrame(Duration.millis(2000), new KeyValue(bullet.translateXProperty(), bulletToX), new KeyValue(bullet.translateYProperty(), bulletToY));

                    KeyFrame keyFrame2 = new KeyFrame(Duration.millis(2000),
                            new EventHandler<>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {


                                    Point2D player22D = player2.localToParent(player2.getX(), player2.getY());
                                    Point2D bullet2D = bullet.localToParent(bullet.getX(), bullet.getY());

                                    System.out.println("distance: " + player22D.distance(bullet2D));
                                    System.out.println("player2.getX(): " + player2.getX());
                                    System.out.println("player2.getY(): " + player2.getY());
                                    System.out.println("bullet.getX(): " + bullet.getX());
                                    System.out.println("bullet.getY(): " + bullet.getY());

//                                    System.out.println("player22D: " + player22D);
//                                    System.out.println("bullet2D: " + bullet2D);
//
//                                    System.out.println("player2.getX(): " + player2.getX());
//                                    System.out.println("bullet.getX(): " + bullet.getX());
//                                    if (player22D.distance(bullet2D)<=0) {
                                    if (player22D.distance(bullet2D) == 0) {
                                        System.out.println("HIT !!!");
                                    }
                                }
                            });

//                    timeline.getKeyFrames().addAll(keyFrame2,keyFrame1, keyFrame3);
//                    timeline.getKeyFrames().addAll(keyFrame1,keyFrame2);


                    timeline.setOnFinished(event -> {
                        root.getChildren().remove(bullet);
                        root.getChildren().add(explode);
                        explodeTransition.play();
                    });

                    timeline.play();

//                    new SequentialTransition (shotTransition, pauseTransition).play();


//


//                    PathTransition transitionBullet = new PathTransition();
//                    transitionBullet.setDuration(Duration.millis(2000));
//                    transitionBullet.setNode(bullet);
//                    transitionBullet.setPath(pathBullet);
//                    transitionBullet.play();

//                    transitionBullet.statusProperty().addListener(new ChangeListener<Integer>() {
//
//                        @Override
//                        public void changed(ObservableValue<? extends Status> observableValue,
//                                            Status oldValue, Status newValue) {
//                            if(newValue==Status.STOPPED){
//                                //do something
//                            }
//                        }
//                    });


//                    Image explodeImage = new Image(inputStreamExplode);
//                    explode.setImage(explodeImage);
//                    explode.setPreserveRatio(true);
//                    explode.setFitWidth(BULLET_EXPLODE);
//                    bullet.setX(player1.getX());
//                    bullet.setY(player1.getY());


//                    explode.setX(explodeX);
//                    explode.setY(explodeY);
//                    explode.setVisible(false);
//                    root.getChildren().add(explode);

//                    FadeTransition explodeTransition = new FadeTransition(Duration.millis(500), explode);
//                    explodeTransition.setFromValue(1.0f);
//                    explodeTransition.setToValue(0f);
//                    explodeTransition.setCycleCount(2);
//
//
//                    SequentialTransition sequential = new SequentialTransition(transitionBullet, explodeTransition);
//                    sequential.play();


//                    bullet.setLayoutX(bulletFromX);
//                    bullet.setLayoutY(bulletFromY);
//                    Timeline timeline = new Timeline(new KeyFrame(
//                            Duration.millis(1),
//                            new EventHandler<>() {
//
//                                double deltaX, deltaY;
//                                double distanceX, distanceY;
//                                double startX = bulletFromX, startY = bulletFromY;
//                                double endX = bulletToX, endY = bulletToY;
//
//                                @Override
//                                public void handle(ActionEvent actionEvent) {
//
//                                    distanceX = endX - startX;
//                                    distanceY = endY - startY;
//
//                                    deltaX = (distanceX - AVATAR_BATTLEFIELD_SIZE) / BATTLEFIELD_WIDTH;
//                                    deltaY = (distanceY - AVATAR_BATTLEFIELD_SIZE) / BATTLEFIELD_HEIGHT;
//
//                                    System.out.println(startX);
//                                    System.out.println(startY);
//                                    System.out.println(endX);
//                                    System.out.println(endY);
//                                    System.out.println(distanceY);
//                                    System.out.println(distanceY);
//                                    System.out.println(deltaX);
//                                    System.out.println(deltaY);
//
//
//                                    bullet.setLayoutX(bullet.getLayoutX() - deltaX);
//                                    bullet.setLayoutY(bullet.getLayoutY() - deltaY);
//
////                                    bullet.setLayoutX(bullet.getLayoutX() + 1);
////                                    bullet.setLayoutY(bullet.getLayoutY() + 5);
//                                }
//                            }
//                    ));
//
//                    timeline.setCycleCount(1);
//                    timeline.play();

//                } catch (IOException e) {
//                    System.err.println("Cannot load bullet image exception (Class RootController): " + e.getMessage());
//                }

                System.out.println(player1.getImage() + " SHOT towards " + player2.getImage());
            }
            System.out.println("DOWN: " + timeline.getStatus());

        };
        return keyShootListener;
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

    private MediaPlayer soundEffect(String path) {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        return player;
    }

    private ImageView createGraphicsFromPath(String path, int width) {
        try (InputStream inputStream = new FileInputStream(path)) {
            return createGraphicsFromInputStream(inputStream,width);
        } catch (IOException e) {
            System.err.println("Cannot load image exception: " + e.getMessage());
            return null;
        }
    }

    private ImageView createGraphicsFromInputStream(InputStream inputStream, int width) {
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setVisible(true);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        return imageView;
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


