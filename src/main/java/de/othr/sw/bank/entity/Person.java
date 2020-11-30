package de.othr.sw.bank.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person extends BaseEntity implements Serializable {
    private String forename;
    private String surname;
    @Column(unique = true)
    private String username;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date birthDate;
    private String password;

    public Person() {
    }

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
