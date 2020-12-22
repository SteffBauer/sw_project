package de.othr.sw.bank.service;

public class AccountNotFoundException extends Exception {

    private String iban;

    public AccountNotFoundException(String iban) {
        this.iban = iban;
    }

    public AccountNotFoundException(String message, String iban) {
        super(message);
        this.iban = iban;
    }


    public String getIban() {
        return iban;
    }

}
