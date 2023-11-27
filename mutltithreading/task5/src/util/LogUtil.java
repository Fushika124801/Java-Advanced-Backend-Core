package util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {

  public static String logBusinessException(String className, String message) {
    Logger.getLogger(className).log(Level.WARNING, message);
    return message;
  }

  public static void logRegularExceptions(String className, Throwable throwable) {
    Logger.getLogger(className).log(Level.SEVERE, throwable.getMessage());
  }

  public static void logInfo(String className, String message) {
    Logger.getLogger(className).log(Level.INFO, message);
  }
}

