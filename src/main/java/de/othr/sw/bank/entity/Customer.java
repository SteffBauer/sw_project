package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private long customerId;
    private String forename;
    private String surname;
    //@ManyToMany
    //private Address address;
    private String taxNumber;
    private String iban;
    private String passwordHash;

    public Customer(){}

    public Customer(String forename, String surname,String street, int houseNr, Integer zipCode, String city, String country, String taxNumber, String password) {
        this.forename = forename;
        this.surname = surname;
        this.taxNumber = taxNumber;
        //this.address=new Address(forename+" "+surname,street,houseNr,zipCode,city,country);
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(Customer.class))
            return false;
        Customer other=(Customer)o;

        return this.customerId==other.customerId;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.customerId);
    }

}
