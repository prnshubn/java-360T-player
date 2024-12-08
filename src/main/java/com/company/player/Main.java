package com.company.player;

import com.company.player.separatepid.InitiatorNode;
import com.company.player.separatepid.ServerNode;
import com.company.player.singlepid.Player;
import com.company.player.util.Logger;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Entry point for the messaging application.
 * <p>
 * For separate process requirement this class determines whether to run
 * as a client connecting to an existing server or as a server if no connection is available.
 * <p>
 * For single process requirement, this class creates two threads within a single process.
 * <p>
 * The application defaults to running as a client and only initializes as a server
 * if a {@link ConnectException} occurs during the connection attempt.
 * </p>
 *
 * @author priyanshu
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length > 0) {
            String input = args[0];
            switch (input) {
                case "1":
                    singleProcessRunner();
                    break;
                case "2":
                    separateProcessRunner();
                    break;
                default:
                    Logger.log("Invalid input. Please enter 1 or 2.");
                    break;
            }
        } else {
            Logger.log("Please run the code only using the provided shell script.");
        }
    }

    /**
     * Initiates the application in single process mode.
     * Creates two Player instances, sets up communication channels,
     * and starts them in separate threads. Sends an initial message
     * to start the conversation and waits for both threads to complete.
     */
    private static void singleProcessRunner() {
        // Create player instances
        Player initiator = new Player("initiator");
        Player receiver = new Player("receiver");

        // Establish communication channels
        initiator.setOutgoingQueue(receiver.getIncomingQueue());
        receiver.setOutgoingQueue(initiator.getIncomingQueue());

        // Start player threads
        Thread t1 = new Thread(initiator);
        Thread t2 = new Thread(receiver);
        t1.start();
        t2.start();

        // Send initial message
        String initialMessage = "[Hello!]";
        Logger.log("----- Initial message sent by initiator: " + initialMessage + " -----");
        initiator.sendMessage(initialMessage);

        // Wait for threads to complete
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        // Log program termination
        Logger.log("Ending program: stop condition achieved.");
    }

    /**
     * Initiates the application in separate process mode.
     * Attempts to connect as a client using {@link InitiatorNode}.
     * If the connection fails, starts the application as a server using {@link ServerNode}.
     *
     * @throws IOException If an I/O error occurs during connection.
     * @throws ClassNotFoundException If a class cannot be found during deserialization.
     */
    private static void separateProcessRunner() throws IOException, ClassNotFoundException {
        try {
            new InitiatorNode(); // Attempt to start initiator connection
        } catch (Exception e) {
            new ServerNode(); // Start server on connection failure
        }
    }
}
