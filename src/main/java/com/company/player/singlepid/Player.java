package com.company.player.singlepid;

import com.company.player.util.Logger;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.company.player.util.Constants.MAX_MESSAGES;

/**
 * Represents a player in a messaging service, capable of sending and receiving messages
 * via concurrent queues. Each player maintains a count of messages exchanged and
 * stops once the predefined maximum is reached.
 * This class implements the {@link Runnable} interface, allowing each player to run
 * independently in its own thread.
 * <p>
 * Note: This class has only been used for the single process requirement.
 *
 * @author priyanshu
 */
public class Player implements Runnable, Serializable {
    private final String name;
    private final BlockingQueue<String> incomingMessages;
    private BlockingQueue<String> outgoingMessages;
    private int messageCounter = 0;

    /**
     * Constructs a new Player with the specified name and initializes the incoming
     * message queue.
     *
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.incomingMessages = new LinkedBlockingQueue<>();
    }

    /**
     * Gets the queue used by this player to receive messages.
     *
     * @return The incoming message queue
     */
    public BlockingQueue<String> getIncomingQueue() {
        return incomingMessages;
    }

    /**
     * Sets the outgoing queue that this player will use to send messages to another player.
     *
     * @param outgoingMessages The queue used for outgoing messages
     */
    public void setOutgoingQueue(BlockingQueue<String> outgoingMessages) {
        this.outgoingMessages = outgoingMessages;
    }

    /**
     * Sends a message to another player using the outgoing message queue.
     *
     * @param message The message to be sent
     */
    public void sendMessage(String message) {
        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The player's main execution logic. Continuously processes incoming messages, appends
     * a reply, and sends it back until the maximum number of messages is reached or the thread
     * is interrupted.
     */
    @Override
    public void run() {
        while (messageCounter < MAX_MESSAGES) {
            try {
                String message = incomingMessages.take(); // Waits for an incoming message
                String reply = message + " [reply" + messageCounter + "]";
                Logger.logWithPid(name + " sending: " + reply);
                sendMessage(reply);
                messageCounter++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}