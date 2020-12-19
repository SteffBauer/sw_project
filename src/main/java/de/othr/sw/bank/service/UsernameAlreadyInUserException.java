package de.othr.sw.bank.service;

public class UsernameAlreadyInUserException extends Exception {
    public UsernameAlreadyInUserException() {
    }

    public UsernameAlreadyInUserException(String message) {
        super(message);
    }
}
