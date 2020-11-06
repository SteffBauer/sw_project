package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transfer {
    @Id
    @GeneratedValue
    private long transferId;
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

    public long getTransferId() {
        return transferId;
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

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(Transfer.class))
            return false;
        Transfer other = (Transfer) o;

        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(transferId);
    }
}
