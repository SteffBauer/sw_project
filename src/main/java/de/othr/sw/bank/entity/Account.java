package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    private final long blz = 100100100;
    private String iban;


    @ManyToOne()
    @JoinColumn(name="customerId")
    private Customer customer;

    public Account(){}

    public Account(Customer customer) {
        this.customer = customer;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getBlz() {
        return blz;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(Account.class))
            return false;
        Account other=(Account) o;

        return this.accountId==other.accountId;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.accountId);
    }

    public void createIban() {
        //todo stellen auffüllen...
        String country= customer.getAddress().getCountry().substring(0,2).toUpperCase();
        String account = String.format("%010d", getAccountId());
        setIban(country+getBlz()+account);
    }
}
