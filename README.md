## Problem Statement
Having a Player class - an instance of this class with that can communicate with other Player(s) (other instances of this class)

The use case for this task is as bellow:

- create 2 players
- one of the players should send a message to second player (let's call this player "initiator")
- when a player receives a message should send back a new message that contains the received message concatenated with the message counter that this player sent.
- finalize the program (gracefully) after the initiator sent 10 messages and received back 10 messages (stop condition)
- both players should run in the same java process (strong requirement)
- document for every class the responsibilities it has.
- opposite to 5: have every player in a separate JAVA process (different PID).

Please use pure Java as much as possible (no additional frameworks like spring, etc.)
Please deliver one single maven project with the source code only (no jars). Please send the maven project as archive attached to e-mail (eventual links for download will be ignored due to security policy).
Please provide a shell script to start the program.
Everything what is not clearly specified is to be decided by developer. Everything what is specified is a hard requirement.
Please focus on design and not on technology, the technology should be the simplest possible that is achieving the target.
The focus of the exercise is to deliver the cleanest and clearest design that you can achieve (and the system has to be functional).

## Table of Contents

- [Classes Overview](#classes-overview)
- [Acknowledgments](#acknowledgments)

## Classes Overview

### Packages
- **separatepid**
    - *BaseNode*: Abstract base class representing a network node (server or client representing two players) in a messaging service. This class manages communication between nodes and provides methods for handling messaging functionality and resource cleanup. Both client and server should extend this class to provide their own implementation of the messaging logic.
  
    - *InitiatorNode*: Represents a client node that connects to the server in a messaging service. This class handles communication with the server and manages the player's messaging logic. It establishes a connection to the server, initializes the player, and handles the messaging process.
  
    - *Player*: Represents a player in the messaging service. This class manages the player's name, messages, and message count, and is capable of generating responses based on incoming messages. Implements Serializable to allow player instances to be transferred across streams. Each Player object maintains a thread-safe message count using AtomicInteger.
    Note: This class can be instantiated using either the default constructor or a parameterized constructor.
    Note: This class has only been used for the separate process requirement.

- **singlepid**
    - *Player*: Represents a player in a messaging service, capable of sending and receiving messages via concurrent queues. Each player maintains a count of messages exchanged and stops once the predefined maximum is reached. This class implements the Runnable interface, allowing each player to run independently in its own thread. 
    Note: This class has only been used for the single process requirement.

- **util**
    - *Constants*: A utility class that holds the constant values used throughout the service. These constants define configuration settings such as network details and player identifiers used in the messaging service. This class cannot be instantiated.
    Note: All values are declared as `public static final`, ensuring they remain unchanged.
    - *Logger*: Utility Logger class for logging messages to the console. This class provides a centralized way to log messages, making it easier to manage logging throughout the application. Currently, it logs messages to the standard output but can be extended to include different logging mechanisms such as logging to a file or integrating with logging frameworks in the future.
    Note: This class is designed with static methods for ease of use without requiring instantiation.
  
- *Main*: Main method is here. Entry point for the messaging application.
  For separate process requirement this class determines whether to run as a client connecting to an existing server or as a server if no connection is available.
  For single process requirement, this class creates two threads within a single process.
  The application defaults to running as a client and only initializes as a server if a ConnectException occurs during the connection attempt.

## How to run the program?
**requirements**
- Java 17 should be installed
- Maven and Java HOME paths should already be set

**run the following commands**
- cd "`full path here`" (till player directory)
- bash ./code_runner.sh

Code starts running, user needs to enter a choice for the mode of communication.
1 -> single PID mode,
2 -> separate PID mode

Code automatically exits after completion

## Acknowledgments
- [Maven](https://maven.apache.org/) for project management and build automation.
- [Java 17](https://www.oracle.com/java/) for the programming language.
