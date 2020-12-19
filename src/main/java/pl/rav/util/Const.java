package pl.rav.util;

public class Const {

    public static final int WELCOME_WIDTH = 500;
    public static final int WELCOME_HEIGHT = 700;

    public static final int MAIN_APP_START_X = 100;
    public static final int MAIN_APP_START_Y = 100;

    public static final int MAIN_APP_WIDTH = 1400;
    public static final int MAIN_APP_HEIGHT = 700;

    public static final int MENU_LEFT_RIGHT_SIZE = 200;
    public static final int MENU_TOP_SIZE = 100;
    public static final int MENU_BOTTOM_SIZE = 100;

    public static final int AVATAR_BATTLEFIELD_SIZE = 50;
    public static final int AVATAR_MENU_HEIGHT = 80;

    public static final int BATTLEFIELD_START_X = MENU_LEFT_RIGHT_SIZE;
    public static final int BATTLEFIELD_START_Y = MENU_TOP_SIZE;
    public static final int BATTLEFIELD_END_X = MAIN_APP_WIDTH - MENU_LEFT_RIGHT_SIZE;
    public static final int BATTLEFIELD_END_Y = MAIN_APP_HEIGHT - MENU_BOTTOM_SIZE;

    public static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_START_Y - BATTLEFIELD_END_Y;
    public static final int BATTLEFIELD_WIDTH = BATTLEFIELD_START_X - BATTLEFIELD_END_X;

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static final String INITIAL_POINTS = "0";

}
