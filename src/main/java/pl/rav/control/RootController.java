package pl.rav.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.rav.Game;
import pl.rav.game.Player;
import pl.rav.game.PlayerHolder;
import pl.rav.util.Avatar;

import java.io.FileInputStream;
import java.io.InputStream;

import static pl.rav.util.Const.*;

public class RootController {

    private Player player;

    @FXML
    private Label nick;
    @FXML
    private ImageView avatar;

//    public RootController(Player player) {
//        this.player = player;
//    }

    @FXML
    private void initialize() {

    }

    @SneakyThrows
    public void goToTheBattle() {
        PlayerHolder playerHolder = PlayerHolder.getInstance();
        Player player = playerHolder.getPlayer();

        nick.setText(player.getNick());

//        InputStream stream = new FileInputStream("C:\\DATA\\!!!_!_DATA_201903\\Coding\\C_School\\POOL\\for_evaluation1\\src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar01.png");
//        InputStream stream = new FileInputStream("src\\main\\resources\\pl\\rav\\graphics\\avatars\\humans\\humanAvatar01.png");
        InputStream stream = new FileInputStream(player.getAvatar().getPath());
        Image image = new Image(stream);

        avatar.setImage(image);
        avatar.setVisible(true);
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(AVATAR_HEIGHT);

    }

}
