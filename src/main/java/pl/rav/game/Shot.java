//package pl.rav.game;
//
//import javafx.animation.*;
//import javafx.event.EventHandler;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.shape.LineTo;
//import javafx.scene.shape.MoveTo;
//import javafx.scene.shape.Path;
//import javafx.util.Duration;
//import lombok.*;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import static pl.rav.util.Const.*;
//
//@RequiredArgsConstructor
//public class Shot implements Runnable {
//    @NonNull private ImageView player1;
//    @NonNull private ImageView player2;
//    @NonNull private KeyCode shot;
//    @NonNull private BorderPane root;
//
//    @Getter
//    private EventHandler<KeyEvent> eventEventHandler = warriorsShoot();
//
//    @Override
//    public void run() {
//        eventEventHandler = warriorsShoot();
//    }
//    private EventHandler<KeyEvent> warriorsShoot() {
//        EventHandler<KeyEvent> keyShootListener = keyEvent -> {
//
//            if (keyEvent.getCode() == shot) {
//
//                String pathBullet = "src/main/resources/pl/rav/graphics/bullets/bullet01.png";
//                String pathExplode = "src/main/resources/pl/rav/graphics/explode/explode02.png";
//
//                int bulletFromX = (int) player1.getX() + AVATAR_BATTLEFIELD_SIZE / 2;
//                int bulletFromY = (int) player1.getY() + AVATAR_BATTLEFIELD_SIZE / 2;
//                int bulletToX = (int) player2.getX();// + AVATAR_BATTLEFIELD_SIZE / 2;
//                int bulletToY = (int) player2.getY();// + AVATAR_BATTLEFIELD_SIZE / 2;
//                int explodeX = (int) player2.getX() + AVATAR_BATTLEFIELD_SIZE / 4;
//                int explodeY = (int) player2.getY() + AVATAR_BATTLEFIELD_SIZE / 4;
//
//                try (
//                        InputStream inputStreamBullet = new FileInputStream(pathBullet);
//                        InputStream inputStreamExplode = new FileInputStream(pathExplode)
//                ) {
//
//                    ImageView bullet = createGraphics(inputStreamBullet, BULLET_WIDTH);
//                    root.getChildren().add(bullet);
//
//
//                    Timeline timeline = new Timeline(
//                            new KeyFrame(Duration.millis(0), new KeyValue(bullet.translateXProperty(), bulletFromX), new KeyValue(bullet.translateYProperty(), bulletFromY)),
//                            new KeyFrame(Duration.millis(2000), new KeyValue(bullet.translateXProperty(), bulletToX), new KeyValue(bullet.translateYProperty(), bulletToY)),
//                            new KeyFrame(Duration.millis(3000))
//                    );
//
//                    soundEffect("src/main/resources/pl/rav/graphics/bullets/bulletSound01.mp3").play();
//                    timeline.play();
//
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                } catch (IOException e) {
//                    System.err.println("Cannot load bullet image exception (Class RootController): " + e.getMessage());
//                }
//
//                System.out.println(player1.getImage() + " SHOT towards " + player2.getImage());
//            }
//
//
//        };
//        return keyShootListener;
//    }
//
//    private MediaPlayer soundEffect(String path) {
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer player = new MediaPlayer(media);
//        return player;
//    }
//
//    private ImageView createGraphics(InputStream inputStream, double width) {
//        Image image = new Image(inputStream);
//        ImageView imageView = new ImageView(image);
//        imageView.setVisible(true);
//        imageView.setPreserveRatio(true);
//        imageView.setFitWidth(width);
//        return imageView;
//    }
//
//
//}
