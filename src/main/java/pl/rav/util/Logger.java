package pl.rav.util;

import java.util.Arrays;

public class Logger {

    private static int lineNumber;
    private static String className;

    public static String log() {
        lineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
        className = Thread.currentThread().getStackTrace()[3].getClassName();
        return "[Class: " + className + ", line no. " + lineNumber + "]";
    }
}
