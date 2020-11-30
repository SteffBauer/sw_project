package de.othr.sw.bank.entity;

import de.othr.sw.bank.utils.EncryptionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Person extends BaseEntity implements Serializable {
    private String forename;
    private String surname;
    @Column(unique=true)
    private String username;
    private Date birthDate;
    private String password;

    @ManyToOne()
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;


    public Person(){}

    public Person(String forename, String surname, String username, Date birthDate, String password) {
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.birthDate = birthDate;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
