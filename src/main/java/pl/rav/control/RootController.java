package pl.rav.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.rav.Game;
import pl.rav.game.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static pl.rav.util.Const.*;

public class RootController implements Initializable {

    @FXML
    private Label playerVsPlayer;
    @FXML
    private Label nickLeft, raceLeft, scoreLeft;
    @FXML
    private Label nickRight, raceRight, scoreRight;
    @FXML
    private ImageView avatarLeft, avatarRight;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPlayerVsPlayer();

        setAvatarLeft(avatarLeft, Game.playerGlobalFirst);
        setNick(nickLeft, Game.playerGlobalFirst);
        setRace(raceLeft, Game.playerGlobalFirst);
        setScore(scoreLeft, Game.playerGlobalFirst, "0");

        setAvatarLeft(avatarRight, Game.playerGlobalSecond);
        setNick(nickRight, Game.playerGlobalSecond);
        setRace(raceRight, Game.playerGlobalSecond);
        setScore(scoreRight, Game.playerGlobalSecond, "0");
    }

    public void goToTheBattle() {

    }

    private void setPlayerVsPlayer () {
        playerVsPlayer.setText(Game.playerGlobalFirst.getNick() + " Vs. " + Game.playerGlobalSecond.getNick());
    }

    private void setNick(Label where, Player player) {
        where.setText(player.getNick());
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

    private void setRace (Label where, Player player) {
        where.setText(player.getRace().get());
    }

    private void setScore(Label where, Player player, String points) {
        where.setText(points);
    }


}
