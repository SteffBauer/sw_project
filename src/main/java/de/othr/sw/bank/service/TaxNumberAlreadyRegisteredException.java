package de.othr.sw.bank.service;

public class TaxNumberAlreadyRegisteredException extends Exception {
    public TaxNumberAlreadyRegisteredException() {
    }

    public TaxNumberAlreadyRegisteredException(String message) {
        super(message);
    }
}
