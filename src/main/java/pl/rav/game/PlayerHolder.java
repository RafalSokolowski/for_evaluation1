package pl.rav.game;

import lombok.ToString;

@ToString
public final class PlayerHolder {

    private Player player;
    private final static PlayerHolder INSTANCE= new PlayerHolder();

    private PlayerHolder() {}

    public static PlayerHolder getInstance() {
        return INSTANCE;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
