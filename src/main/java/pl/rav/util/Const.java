package pl.rav.util;

public class Const {

    public static final int WELCOME_WIDTH = 500;
    public static final int WELCOME_HEIGHT = 700;

    public static final int END_WIDTH = WELCOME_WIDTH - 100;

    public static final int MAIN_APP_START_X = 100;
    public static final int MAIN_APP_START_Y = 100;

    public static final int MAIN_APP_WIDTH = 1400;
    public static final int MAIN_APP_HEIGHT = 700;

    public static final int MENU_LEFT_RIGHT_SIZE = 200;
    public static final int MENU_TOP_SIZE = 100;
    public static final int MENU_BOTTOM_SIZE = 100;

    public static final double AVATAR_BATTLEFIELD_SIZE = 50.0;
    public static final int AVATAR_MENU_SIZE = 80;

    public static final int BULLET_HEIGHT = 30;
    public static final int BULLET_WIDTH = 20;
    public static final int BULLET_RANGE = 300;
    public static final int EXPLODE_WIDTH = 40;

    public static final double SHOT_DURATION = 100.0;


    public static final int BATTLEFIELD_START_X = MENU_LEFT_RIGHT_SIZE;
    public static final int BATTLEFIELD_START_Y = MENU_TOP_SIZE;
    public static final int BATTLEFIELD_END_X = MAIN_APP_WIDTH - MENU_LEFT_RIGHT_SIZE;
    public static final int BATTLEFIELD_END_Y = MAIN_APP_HEIGHT - MENU_BOTTOM_SIZE;

//    public static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_START_Y - BATTLEFIELD_END_Y;
    public static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_END_Y - BATTLEFIELD_START_Y;
//    public static final int BATTLEFIELD_WIDTH = BATTLEFIELD_START_X - BATTLEFIELD_END_X;
    public static final int BATTLEFIELD_WIDTH = BATTLEFIELD_END_X - BATTLEFIELD_START_X;

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static final String INITIAL_POINTS = "0";
    public static final int INITIAL_HEALTH = 1;

    // SOUNDS
    public static final String SHOT_SOUND = "src/main/resources/pl/rav/graphics/bullets/bulletSound01.mp3";

    // GRAPHICS
    public static final String BULLET_PATH = "src/main/resources/pl/rav/graphics/bullets/bullet01.png";
    public static final String EXPLODE_PATH = "src/main/resources/pl/rav/graphics/explode/explode02.png";

}
