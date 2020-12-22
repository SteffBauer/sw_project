package de.othr.sw.bank.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity(name = "customer")
public class Customer extends Person {

    @Column(unique = true)
    private String taxNumber;

    @ManyToOne()
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "attendant")
    private Employee attendant;


    public Customer() {
    }

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


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(PersonAuthority authority : this.personAuthorities) {
            authorities.add(new Authority(authority.getRight().getPrivilegeName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Employee getAttendant() {
        return attendant;
    }

    public void setAttendant(Employee attendant) {
        this.attendant = attendant;
    }

    public String getFullName(){
        return getSurname()+" "+getUsername();
    }
}
