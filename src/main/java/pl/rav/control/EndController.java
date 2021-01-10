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
import pl.rav.game.Player;
import pl.rav.util.Graphics;

import java.io.IOException;
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

        Label statisticsWin, statisticsLose;

        if (Game.playerGlobalFirst.isJustWon()) {
//            nickWon.setText(Game.playerGlobalFirst.getNick() + " has won");
//            nickWon.setStyle("-fx-font: 30px Tohoma; -fx-font-weight: bold;");
//            end.add(graphics.createGraphicsFromPath(Game.playerGlobalFirst.getAvatar().getPath(), AVATAR_MENU_SIZE),0,1);
//            statisticsWin = new Label("Wins: " + Game.playerGlobalFirst.getWins() + ",    Loses: " + Game.playerGlobalFirst.getLoses());
//            statisticsWin.setStyle("-fx-font: 15px Tohoma; -fx-font-weight: bold;");
//            end.add(statisticsWin,1,1);
//
//            nickLose.setText(Game.playerGlobalSecond.getNick() +  " has lose");
//            nickLose.setStyle("-fx-font: 30px Tohoma; -fx-font-weight: bold;");
//            end.add(graphics.createGraphicsFromPath(Game.playerGlobalSecond.getAvatar().getPath(), AVATAR_MENU_SIZE),0,3);
//            statisticsLose = new Label("Wins: " + Game.playerGlobalSecond.getWins() + ",    Loses: " + Game.playerGlobalSecond.getLoses());
//            statisticsLose.setStyle("-fx-font: 15px Tohoma; -fx-font-weight: bold;");
//            end.add(statisticsLose,1,3);
            setData(Game.playerGlobalFirst);

        } else {
            setData(Game.playerGlobalSecond);
//            nickWon.setText(Game.playerGlobalSecond.getNick() + " has won");
//            nickWon.setStyle("-fx-font: 30px Tohoma; -fx-font-weight: bold;");
//            end.add(graphics.createGraphicsFromPath(Game.playerGlobalSecond.getAvatar().getPath(), AVATAR_MENU_SIZE),0,1);
//            nickLose.setText(Game.playerGlobalFirst.getNick() + " has lose");
//            nickLose.setStyle("-fx-font: 15px Tohoma; -fx-font-weight: bold;");
//            end.add(graphics.createGraphicsFromPath(Game.playerGlobalFirst.getAvatar().getPath(), AVATAR_MENU_SIZE),0,3);
        }

        Game.playerGlobalFirst.setJustWon(false);
        Game.playerGlobalSecond.setJustWon(false);

        Game.playerGlobalFirst.resetHealth();
        Game.playerGlobalSecond.resetHealth();

    }

    public void playAgain() throws IOException {
        Game.setRoot("root", MAIN_APP_WIDTH, MAIN_APP_HEIGHT, MAIN_APP_START_X, MAIN_APP_START_Y);
    }

    private void setData(Player whoWon) {

        Graphics graphics = new Graphics();

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        end.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints ();
        rowConstraints.setValignment(VPos.CENTER);
        end.getRowConstraints().add(rowConstraints);

        Label statisticsWin, statisticsLose;

        Player whoLose = getOpponent(whoWon);

        nickWon.setText(whoWon.getNick() + " has won");
        nickWon.setStyle("-fx-font: 30px Tohoma; -fx-font-weight: bold;");
        end.add(graphics.createGraphicsFromPath(whoWon.getAvatar().getPath(), AVATAR_MENU_SIZE),0,1);
        statisticsWin = new Label("Wins: " + whoWon.getWins() + ",    Loses: " + whoWon.getLoses());
        statisticsWin.setStyle("-fx-font: 15px Tohoma; -fx-font-weight: bold;");
        end.add(statisticsWin,1,1);

        nickLose.setText(whoLose.getNick() +  " has lose");
        nickLose.setStyle("-fx-font: 30px Tohoma; -fx-font-weight: bold;");
        end.add(graphics.createGraphicsFromPath(whoLose.getAvatar().getPath(), AVATAR_MENU_SIZE),0,3);
        statisticsLose = new Label("Wins: " + whoLose.getWins() + ",    Loses: " + whoLose.getLoses());
        statisticsLose.setStyle("-fx-font: 15px Tohoma; -fx-font-weight: bold;");
        end.add(statisticsLose,1,3);
    }

    private Player getOpponent (Player player) {
        return player.equals(Game.playerGlobalFirst) ? Game.playerGlobalSecond : Game.playerGlobalFirst;
    }


}