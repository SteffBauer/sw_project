package de.othr.sw.bank.service;

public class UsernameAlreadyInUseException extends Exception {
    public UsernameAlreadyInUseException() {
    }

    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
