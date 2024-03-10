package org.example.Exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super("Invalid command. Please enter a valid command.");
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}