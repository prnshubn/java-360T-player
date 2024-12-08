#!/usr/bin/env bash

#fresh initialization
mvn clean install

# Prompt the user for input
echo "Enter 1 for single process communication (single PID)"
echo "Enter 2 for different process communication (separate PID)"
read -p "Enter your choice: " input
echo "You entered: $input"

# Validate the input
if [[ "$input" == "1" ]]; then
    echo "Single Process Communication within a single PID"
    java -jar target/player-0.0.1-SNAPSHOT.jar "$input" &
elif [[ "$input" == "2" ]]; then
    echo "Separate Process Communication with different PIDs"
    java -jar target/player-0.0.1-SNAPSHOT.jar "$input" &
    echo "Server started with PID $!"
    sleep 2
    java -jar target/player-0.0.1-SNAPSHOT.jar "$input" &
    echo "Initiator started with PID $!"
else
    echo "Invalid input. Please enter only 1 or 2."
fi

sleep 1
echo "Exiting script..."
exit 1