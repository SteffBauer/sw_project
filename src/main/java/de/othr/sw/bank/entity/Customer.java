package de.othr.sw.bank.entity;

import de.othr.sw.bank.utils.EncryptionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    private String forename;
    private String surname;
    private String taxNumber;
    private String passwordHash;

    @ManyToOne()
    @JoinColumn(name="addressId")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    public Customer(){}

    public Customer(String forename, String surname, String taxNumber, String password) {
        this.forename = forename;
        this.surname = surname;
        this.taxNumber = taxNumber;
        this.passwordHash = EncryptionUtils.getEncryptedString(password);
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


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
