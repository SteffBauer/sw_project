package de.othr.sw.bank.entity;

import de.othr.sw.bank.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person extends BaseEntity implements Serializable, UserDetails {
    private String forename;
    private String surname;
    @Column(unique = true)
    private String username;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date birthDate;
    private String password;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<PersonAuthority> personAuthorities;

    public Person() {
        this.active =true;
    }

    public Person(String forename, String surname, String username, Date birthDate, String password) {
        this();
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.birthDate = DateUtils.formatDate(birthDate);
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
