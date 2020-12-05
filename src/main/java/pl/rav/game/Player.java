package pl.rav.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.rav.util.Avatar;
import pl.rav.util.Race;

@Getter
@ToString(includeFieldNames = false)
@AllArgsConstructor
public class Player {

    private final String nick;
    private final Race race;
    private final Avatar avatar;

}
