package pl.rav.game;

import javafx.scene.image.ImageView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.rav.util.Avatar;
import pl.rav.util.Race;

import static pl.rav.util.Const.INITIAL_HEALTH;

@Getter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public class Player {

    private final String nick;
    private final Race race;
    private final Avatar avatar;

    private ImageView onTheBattlefield;
    private int health;
    private int wins;
    private int loses;

    @Setter private boolean justWon;

    public Player(String nick, Race race, Avatar avatar) {
        this.nick = nick;
        this.race = race;
        this.avatar = avatar;

        this.onTheBattlefield = null;
        this.health = INITIAL_HEALTH;
        this.wins = 0;
        this.loses = 0;

        this.justWon = false;
    }

    public void reduceHealth() {
        health--;
    }

    public void increaseWins() {
        wins++;
    }

    public void increaseLoses() {
        loses++;
    }

    public void resetHealth() {
        health = INITIAL_HEALTH;
    }

    public void setWinsAndLosesBetweenControllers(int wins, int loses) {
        this.wins = wins;
        this.loses = loses;
    }

    public boolean isDeath() {
        return health <= 0;
    }

    public void setOnTheBattlefield(ImageView onTheBattlefield) {
        this.onTheBattlefield = onTheBattlefield;
    }
}
