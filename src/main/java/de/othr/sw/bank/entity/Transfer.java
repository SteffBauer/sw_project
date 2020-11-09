package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transfer extends BaseEntity{
    private double amount;
    private Date date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "payer_account_id")
    private Account payerAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

    public Transfer() {
    }

    public Transfer(double amount, String description, Account payerAccount, Account receiverAccount) {
        this.amount = amount;
        this.description = description;
        this.payerAccount = payerAccount;
        this.receiverAccount = receiverAccount;
        this.date = new Date();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(Account payerAccount) {
        this.payerAccount = payerAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

}
