package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Account extends BaseEntity implements Serializable {
    private String iban;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


    @OneToMany(mappedBy="payerAccount")
    private List<Transfer> payers;

    @OneToMany(mappedBy="receiverAccount")
    private List<Transfer> receivers;

    private long balance;

    public Account(){
        // Set default balance to 1.000,00 €
        this.balance = 100000L;
    }

    public Account(Customer customer) {
        this();
        this.customer = customer;
    }

    public long getBlz() {
        long blz = 100100100;
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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void createIban() {
        String country= customer.getAddress().getCountry().substring(0,2).toUpperCase();
        String account = String.format("%010d", this.getId());
        setIban(country+getBlz()+account);
    }
}
