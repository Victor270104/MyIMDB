package org.example.Exceptions;

import javax.swing.*;

public class InformationIncompleteException extends Exception {
    public InformationIncompleteException() {
        super("Information is incomplete. Please provide all required details.");
    }

    public InformationIncompleteException(String message) {
        super(message);
    }
}
