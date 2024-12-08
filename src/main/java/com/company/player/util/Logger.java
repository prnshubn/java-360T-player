package com.company.player.util;

/**
 * Utility Logger class for logging messages to the console. This class provides a
 * centralized way to log messages, making it easier to manage logging throughout the application.
 * Currently, it logs messages to the standard output but can be extended to include different
 * logging mechanisms such as logging to a file or integrating with logging frameworks in the future.
 * <p>
 * Note: This class is designed with static methods for ease of use without requiring instantiation.
 *
 * @author priyanshu
 */
public class Logger {

    /**
     * Logs a message to the console.
     *
     * @param message The message to be logged.
     */
    public static void log(String message) {
        System.out.println(message);
    }

    /**
     * Logs a message to the console along with the PID
     *
     * @param message The message to be logged.
     */
    public static void logWithPid(String message){
        long pid = ProcessHandle.current().pid();
        System.out.println("PID {"+pid+"} "+message);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Logger() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
