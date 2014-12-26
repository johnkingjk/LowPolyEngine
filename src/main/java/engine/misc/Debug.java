package engine.misc;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright by michidk
 * Created: 26.12.2014.
 */
public class Debug {

    private static final Logger logger = Logger.getLogger("LowPolyEngine");

    private static final String SPACER = " - ";

    public static void LogInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void LogWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void LogError(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void Log(Level level, String message) {
        logger.log(level, message);
    }

    public static void Log(Level level, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String s : messages) {
            sb.append(s);
            sb.append(SPACER);
        }
        String complete = sb.toString();
        logger.log(level, complete.substring(0, complete.length() - SPACER.length()));
    }

    public static void Log(Object object) {
        logger.log(Level.INFO, object.toString());
    }

    public static void Log(Level level, Object object) {
        logger.log(level, object.toString());
    }

    public static void Log(Level level, Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objects) {
            sb.append(o.toString());
            sb.append(SPACER);
        }
        String complete = sb.toString();
        logger.log(level, complete.substring(0, complete.length() - SPACER.length()));
    }

}
