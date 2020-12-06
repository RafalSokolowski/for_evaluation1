package pl.rav;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pl.rav.game.Player;

import java.io.IOException;

import static pl.rav.util.Const.*;

public class Game extends Application {

    private static Scene scene;
    public static Player playerGlobalFirst;
    public static Player playerGlobalSecond;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("welcome"), WELCOME_WIDTH, WELCOME_HEIGHT);
        stage.setTitle("simple mini strategy game");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();

    }

    public static void setRoot(String fxmlFile, double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxmlFile));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        scene.getWindow().setX(MAIN_APP_START_X);
        scene.getWindow().setY(MAIN_APP_START_Y);
    }

//    public static void setRoot(String fxmlFile, double width, double height) throws IOException {
//        scene.setRoot(loadFXML(fxmlFile));
//        scene.getWindow().setWidth(width);
//        scene.getWindow().setHeight(height);
//    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource(fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}