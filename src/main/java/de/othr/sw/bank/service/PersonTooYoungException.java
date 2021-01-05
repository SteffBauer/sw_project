package de.othr.sw.bank.service;

public class PersonTooYoungException extends Exception {
    public PersonTooYoungException() {
    }

    public PersonTooYoungException(String message) {
        super(message);
    }
}
