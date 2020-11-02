package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {
    @Id
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
