package com.company.player.separatepid;//package com.company.player;

import com.company.player.util.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Abstract base class representing a network node (server or client representing two players)
 * in the messaging service.
 * This class manages communication between nodes and provides methods for handling
 * messaging functionality and resource cleanup. Both client and server should extend this class
 * to provide their own implementation of the messaging logic.
 *
 * @author priyanshu
 */

public abstract class BaseNode implements Closeable {
    protected Socket socket;
    protected Player player1;
    protected Player player2;

    /**
     * Handles chat communication between nodes (server/client).
     * The implementation details differ based on the role.
     *
     * @param player                  The player involved in the chat communication.
     * @param objectInputStream       Input stream to receive messages.
     * @param objectOutputStream      Output stream to send messages.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    protected abstract void handleMessaging(Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException;

    /**
     * Cleans up resources and exits the application gracefully.
     */
    protected void exitApplication() {
        close();
        System.exit(0);
    }

    /**
     * Closes the socket and releases resources.
     */
    @Override
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            Logger.log("Error closing socket: " + e.getMessage());
        }
    }
}