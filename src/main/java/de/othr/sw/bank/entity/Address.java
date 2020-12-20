package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Address extends BaseEntity {
    private String street;
    private int houseNr;
    private long zipCode;
    private String city;
    private String country;

    @OneToMany(mappedBy = "address")
    private List<Customer> residents;

    public Address(String street, Integer houseNr, Integer zipCode, String city, String country) {
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address() {
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public long getZipCode() {
        return zipCode;
    }

    public void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Customer> getResidents() {
        return Collections.unmodifiableList(this.residents);
    }

    public void setResidents(List<Customer> residents) {
        this.residents = residents;
    }

    public void addResident(Customer c) {
        if (!this.getResidents().contains(c))
            this.getResidents().add(c);
    }
}