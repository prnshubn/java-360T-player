package com.company.player.separatepid;

import com.company.player.util.Constants;
import com.company.player.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Represents a client node that connects to the server in a messaging service.
 * This class handles communication with the server and manages the player's messaging logic.
 * It establishes a connection to the server, initializes the player, and handles the messaging process.
 *
 * @author priyanshu
 */
public class InitiatorNode extends BaseNode {

    /**
     * Constructs a InitiatorNode and establishes a connection to the server.
     * Initializes the player instance and starts the chat process.
     *
     * @throws IOException            If an I/O error occurs while connecting to the server.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public InitiatorNode() throws Exception {
        try (Socket socket = new Socket(Constants.HOST, Constants.PORT);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {

            player1 = new Player();
            player1.setName(Constants.PLAYER_1);
            player1.initializeMessage();

            // Send the first message
            player1.generateResponseFor(player1);
            Logger.log("Sending from Initiator: " + player1.getMessage());
            objectOutputStream.writeObject(player1);

            handleMessaging(player1, objectInputStream, objectOutputStream);
        } finally {
            // Ensuring that initiator resources are closed properly
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }


    /**
     * Handles the chat communication between the client and the server.
     * Continuously listens for incoming messages and sends responses until the chat ends.
     *
     * @param player             The player involved in the chat communication.
     * @param objectInputStream  Input stream to receive messages from the server.
     * @param objectOutputStream Output stream to send messages to the server.
     * @throws IOException            If an I/O error occurs during communication.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    protected void handleMessaging(Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        while ((player2 = (Player) objectInputStream.readObject()) != null) {
            // Check if the maximum message count has been reached
            if (player2.getMessageCount().intValue() == Constants.MAX_MESSAGES && player.getMessageCount().intValue() == Constants.MAX_MESSAGES) {
                Logger.log("Closing Initiator");
                exitApplication();
            }
            // Generate response for the received message
            player.generateResponseFor(player2);
            Logger.logWithPid("Sending from Initiator: " + player.getMessage());
            objectOutputStream.reset();
            objectOutputStream.writeObject(player);
        }
    }
}