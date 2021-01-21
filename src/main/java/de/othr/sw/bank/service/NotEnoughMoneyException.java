package de.othr.sw.bank.service;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
