package com.company.player.separatepid;

import com.company.player.util.Constants;
import com.company.player.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents the server node in the messaging service. This class creates a server that waits
 * for client connections and handles message exchanges with the connected client.
 * The server node initializes the player and manages chat interactions.
 * It listens for incoming client connections and handles communication by sending and
 * receiving messages until the session reaches its defined end.
 * <p>
 * Note: This server operates on a port defined in the {@link Constants} class.
 *
 * @author Priyanshu
 */
public class ServerNode extends BaseNode {
    private ServerSocket serverSocket;

    /**
     * Constructs a ServerNode, sets up the server socket, and waits for a client connection.
     * Once connected, it initializes a player instance and begins the chat process.
     *
     * @throws IOException            If an I/O error occurs while setting up the server socket or streams.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public ServerNode() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT);
             Socket socket = serverSocket.accept();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {

            player1 = new Player();
            player1.setName(Constants.PLAYER_2);
            player1.initializeMessage();

            handleMessaging(player1, objectInputStream, objectOutputStream);
        } finally {
            // Ensuring that server resources are closed properly
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    /**
     * Handles the chat communication with the connected client. Listens for incoming messages
     * from the client, generates a response, and sends it back. Checks whether the chat has
     * reached the maximum message count to finalize the session.
     *
     * @param player             The player object representing the server's player.
     * @param objectInputStream  Input stream to receive messages from the client.
     * @param objectOutputStream Output stream to send messages to the client.
     * @throws IOException            If an I/O error occurs during communication.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    protected void handleMessaging(Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        while ((player2 = (Player) objectInputStream.readObject()) != null) {
            // Generate and send the server's response
            player.generateResponseFor(player2);
            Logger.logWithPid("Sending from Server: " + player.getMessage());
            objectOutputStream.reset();
            objectOutputStream.writeObject(player);

            // Check if the chat has reached the maximum number of messages
            if (player2.getMessageCount().intValue() == Constants.MAX_MESSAGES && player.getMessageCount().intValue() == Constants.MAX_MESSAGES) {
                Logger.log("Closing Server");
                exitApplication();// End the application when the message limit is reached
            }
        }
    }
}