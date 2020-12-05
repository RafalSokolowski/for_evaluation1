package pl.rav.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.rav.Game;
import pl.rav.game.Player;
import pl.rav.game.PlayerHolder;
import pl.rav.util.Avatar;
import pl.rav.util.Race;

import java.io.IOException;

import static pl.rav.util.Const.*;

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

    private Player player;

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
        String playerNick = nick.getText();
        String playerAvatar = generateAvatarNameFromString(avatarButtonPressed);

        Player player = new Player(playerNick, playerRace, Avatar.valueOf(playerAvatar));
        Game.setRoot("root",MAIN_APP_WIDTH, MAIN_APP_HEIGHT);

        PlayerHolder playerHolder = PlayerHolder.getInstance();
        playerHolder.setPlayer(player);

        System.out.println("WelcomeController: " + player);
        System.out.println("WelcomeController (holder): " + playerHolder);

    }

    private String generateAvatarNameFromString(String string) {
        return string.split("\'")[1].replaceAll(" ", "_").toUpperCase();
    }


}