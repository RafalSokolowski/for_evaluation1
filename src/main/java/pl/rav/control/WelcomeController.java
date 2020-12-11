package pl.rav.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.rav.Game;
import pl.rav.game.Player;
import pl.rav.util.Avatar;
import pl.rav.util.Race;

import java.io.IOException;

import static pl.rav.util.Const.*;
import static pl.rav.util.Const.WELCOME_HEIGHT;

public class WelcomeController {

    @FXML
    private TextField nick;
    @FXML
    private Button monster, human;
    @FXML
    private VBox monsterSelected, humanSelected;
    @FXML
    private Button moveFurtherButton;

    private Race playerRace;
    private String avatarButtonPressed;

    public void raceSelected(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(monster)) {
            humanSelected.setVisible(false);
            monsterSelected.setVisible(true);
            playerRace = Race.MONSTERS;
        }
        if (actionEvent.getSource().equals(human)) {
            monsterSelected.setVisible(false);
            humanSelected.setVisible(true);
            playerRace = Race.HUMANS;
        }
    }

    public void avatarSelected(ActionEvent actionEvent) {
        avatarButtonPressed = actionEvent.getSource().toString();
        moveFurtherButton.setVisible(true);
//        System.out.println(generateAvatarNameFromString(avatarButtonPressed));
    }

    public void moveFurther() throws IOException {
        String playerNick;
        String playerAvatar;

        if (Game.playerGlobalFirst == null) {
            playerNick = nick.getText();
            playerAvatar = generateAvatarNameFromString(avatarButtonPressed);
            Game.playerGlobalFirst = new Player(playerNick, playerRace, Avatar.valueOf(playerAvatar));
            Game.setRoot("welcome",WELCOME_WIDTH+15, WELCOME_HEIGHT+35);    // TODO: nie wiem dlaczego musze przesunąć o te współrzędne ręcznie (?)

        } else if (Game.playerGlobalSecond == null) {
            playerNick = nick.getText();
            playerAvatar = generateAvatarNameFromString(avatarButtonPressed);
            Game.playerGlobalSecond = new Player(playerNick, playerRace, Avatar.valueOf(playerAvatar));
            Game.setRoot("root", MAIN_APP_WIDTH, MAIN_APP_HEIGHT, MAIN_APP_START_X, MAIN_APP_START_Y);
        }

        System.out.println("First: " + Game.playerGlobalFirst);
        System.out.println("Second: " + Game.playerGlobalSecond);
    }

    private String generateAvatarNameFromString(String string) {
        return string.split("\'")[1].replaceAll(" ", "_").toUpperCase();
    }

}