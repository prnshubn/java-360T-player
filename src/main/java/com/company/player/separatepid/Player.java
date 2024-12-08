package com.company.player.separatepid;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a player in the messaging service. This class manages the player's name,
 * messages, and message count, and is capable of generating responses based on incoming messages.
 * Implements {@link Serializable} to allow player instances to be transferred across streams.
 * Each Player object maintains a thread-safe message count using {@link AtomicInteger}.
 * <p>
 * Note: This class can be instantiated using either the default constructor or a parameterized constructor.
 * <p>
 * Note: This class has only been used for the separate process requirement.
 *
 * @author priyanshu
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization compatibility

    private String name;
    private String message;
    private AtomicInteger messageCount = new AtomicInteger(0);

    /**
     * Default constructor for creating a new player instance.
     */
    public Player() {
    }

    /**
     * Parameterized constructor to create a new player with specified attributes.
     *
     * @param name         The name of the player.
     * @param message      The initial message for the player.
     * @param messageCount The initial message count of the player.
     */
    public Player(String name, String message, AtomicInteger messageCount) {
        this.name = name;
        this.message = message;
        this.messageCount = messageCount;
    }

    /**
     * Retrieves the player's current message count.
     *
     * @return The message count as an {@link AtomicInteger}.
     */
    public AtomicInteger getMessageCount() {
        return messageCount;
    }

    /**
     * Retrieves the player's current message.
     *
     * @return The current message as a {@link String}.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the player's message.
     *
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Increments the player's message count by 1.
     */
    public void incrementMessageCount() {
        this.messageCount.incrementAndGet();
    }

    /**
     * Generates a response based on the incoming message from another player
     * and increments the player's message count.
     *
     * @param sender The player who sent the incoming message.
     */
    public void generateResponseFor(Player sender) {
        String response = sender.getMessage() + " [reply" + this.getMessageCount() + "]";
        incrementMessageCount();
        this.setMessage(response);
    }

    /**
     * Initializes the player's message with a default greeting.
     *
     * @return The current player instance with an initialized message.
     */
    public Player initializeMessage() {
        this.message = "Hello!";
        return this;
    }

    /**
     * Sets the player's name.
     *
     * @param name The name to be set for the player.
     */
    public void setName(String name) {
        this.name = name;
    }
}