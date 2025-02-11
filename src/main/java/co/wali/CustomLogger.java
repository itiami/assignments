package co.wali;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class CustomLogger {
    private static final String LOG_DIR = "log";
    private static final String LOG_FILE = LOG_DIR + "/application.log";

    // Static block to ensure log directory exists
    static {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    // Private constructor to prevent instantiation
    private CustomLogger() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Static method for logging errors
    public static void logError(String message, Exception e) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(message);
            e.printStackTrace(writer);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
