package de.othr.sw.bank.entity;

import de.othr.sw.bank.utils.EncryptionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Customer extends BaseEntity implements Serializable {
    private String forename;
    private String surname;
    private String taxNumber;
    private String passwordHash;
    @Transient
    private String password;

    @ManyToOne()
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    public Customer(){}

    public Customer(String forename, String surname, String taxNumber, String password) {
        this.forename = forename;
        this.surname = surname;
        this.taxNumber = taxNumber;
        this.password = password;
        this.passwordHash = EncryptionUtils.getEncryptedString(password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
