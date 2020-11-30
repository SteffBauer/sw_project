package de.othr.sw.bank.entity;

import java.util.Date;

public class Customer extends Person {

    private String taxNumber;


    public Customer(String forename, String surname, String username, Date birthDate, String password, String taxNumber) {
        super(forename, surname, username, birthDate, password);
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
}
