package pl.rav.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import pl.rav.Game;
import pl.rav.util.Graphics;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.rav.util.Const.*;

public class EndController implements Initializable {

    @FXML
    GridPane end;

    @FXML
    private Label nickWon, nickLose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Graphics graphics = new Graphics();

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        end.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints ();
        rowConstraints.setValignment(VPos.CENTER);
        end.getRowConstraints().add(rowConstraints);

        if (Game.playerGlobalFirst.isJustWon()) {
            nickWon.setText(Game.playerGlobalFirst.getNick());
            end.add(graphics.createGraphicsFromPath(Game.playerGlobalFirst.getAvatar().getPath(), AVATAR_MENU_SIZE),0,0);
            nickLose.setText(Game.playerGlobalSecond.getNick());
            end.add(graphics.createGraphicsFromPath(Game.playerGlobalSecond.getAvatar().getPath(), AVATAR_MENU_SIZE),0,2);

        } else {
            nickWon.setText(Game.playerGlobalSecond.getNick());
            end.add(graphics.createGraphicsFromPath(Game.playerGlobalSecond.getAvatar().getPath(), AVATAR_MENU_SIZE),0,0);
            nickLose.setText(Game.playerGlobalFirst.getNick());
            end.add(graphics.createGraphicsFromPath(Game.playerGlobalFirst.getAvatar().getPath(), AVATAR_MENU_SIZE),0,2);
        }

        Game.playerGlobalFirst.setJustWon(false);
        Game.playerGlobalSecond.setJustWon(false);


    }

    public void playAgain() {

    }


}