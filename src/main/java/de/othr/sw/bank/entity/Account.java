package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {
    @TableGenerator(name = "BLZ_Gen", table = "ID_GEN", pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL", pkColumnValue = "Iban_Gen", initialValue = 100000,
            allocationSize = 1)
    @TableGenerator(name = "ACC_Id_Gen", table = "ID_GEN", pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL", pkColumnValue = "ACC_Id_Gen", initialValue = 100000,
            allocationSize = 1)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BLZ_Gen")
    private long blz;
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ACC_Id_Gen")
    private long acc_Id;
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

    public void setBlz(long blz) {
        this.blz = blz;
    }

    public long getAcc_Id() {
        return acc_Id;
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
}
