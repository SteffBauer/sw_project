/*
package de.othr.sw.bank.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Address {
    @Id
    private Integer id;
    private String name;
    private String street;
    private Integer houseNr;
    private Integer zipCode;
    private String city;
    private String country;

    @ManyToMany(mappedBy = "address")
    private List<Customer> residents;

    public Address(String name, String street, Integer houseNr,Integer zipCode, String city, String country) {
        this.name = name;
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode=zipCode;
        this.city=city;
        this.country = country;
    }

    public Address() { }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
*/
