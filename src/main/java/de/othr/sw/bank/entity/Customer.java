package de.othr.sw.bank.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "customer")
public class Customer extends Person {

    @Column(unique = true)
    private String taxNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // ,orphanRemoval = true)
    // Potential use case for "orphanRemoval", which is not useful,
    // because the transfers of this account would also lose this reference
    // -> The history would be lost
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

    public List<Account> getActiveAccounts() {
        return accounts.stream().filter(x -> x.isActive()).collect(Collectors.toUnmodifiableList());
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (PersonAuthority authority : this.personAuthorities) {
            authorities.add(new Authority(authority.getRight().getPrivilegeName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }


    public Employee getAttendant() {
        return attendant;
    }

    public void setAttendant(Employee attendant) {
        this.attendant = attendant;
    }

    public String getFullName() {
        return getSurname() + " " + getUsername();
    }


    public void addAccounts(Account a) {
        if (!this.accounts.contains(a))
            this.accounts.add(a);
    }

    public void removeAccount(Account a) {
        this.accounts.remove(a);
    }
}
