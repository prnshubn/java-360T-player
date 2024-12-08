package com.company.player.util;

/**
 * A utility class that holds the constant values used throughout the service.
 * These constants define configuration settings such as network details and
 * player identifiers used in the messaging service.
 * This class cannot be instantiated.
 * <p>
 * Note: All values are declared as `public static final`, ensuring they remain unchanged.
 *
 * @author priyanshu
 */
public final class Constants {

    /**
     * Maximum number of messages allowed in the game session.
     */
    public static final int MAX_MESSAGES = 10;

    /**
     * Port number used for establishing the server-initiator connection.
     */
    public static final int PORT = 8080;

    /**
     * Host address for the server connection.
     */
    public static final String HOST = "localhost";

    /**
     * Name of the second player (initiator side) in the chat-based game.
     */
    public static final String PLAYER_1 = "Initiator";

    /**
     * Name of the first player (server side) in the chat-based game.
     */
    public static final String PLAYER_2 = "Server";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
