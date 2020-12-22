package de.othr.sw.bank.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Transfer extends BaseEntity{
    private long amount;
    private Date date;
    private Date dateCreated;
    private String description;

    @ManyToOne
    @JoinColumn(name = "payer_account_id")
    private Account payerAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

    public Transfer() {
    }

    public Transfer(long amount, String description, Account payerAccount, Account receiverAccount) {
        this.dateCreated= new Date();
        this.amount = amount;
        this.description = description;
        this.payerAccount = payerAccount;
        this.receiverAccount = receiverAccount;
        this.date = new Date();
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
