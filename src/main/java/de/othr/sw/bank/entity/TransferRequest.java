package de.othr.sw.bank.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TransferRequest {
    private String receiverSurname;
    private String receiverForename;

    private String receiverIban;
    private String iban;
    private long amount;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date date;
    private String description;

    public TransferRequest(){}

    public TransferRequest(String receiverSurname, String receiverForename, String receiverIban, String iban, long amount, Date date, String description) {
        this.receiverSurname = receiverSurname;
        this.receiverForename = receiverForename;
        this.receiverIban = receiverIban;
        this.iban = iban;
        this.amount = amount;
        this.date = date;
        this.description= description;
    }

    public String getReceiverSurname() {
        return receiverSurname;
    }

    public void setReceiverSurname(String receiverSurname) {
        this.receiverSurname = receiverSurname;
    }

    public String getReceiverForename() {
        return receiverForename;
    }

    public void setReceiverForename(String receiverForename) {
        this.receiverForename = receiverForename;
    }

    public String getIban() {
        return iban != null ? iban.toUpperCase() : null;
    }

    public void setIban(String iban) {
        this.iban = iban.toUpperCase();
    }

    public String getReceiverIban() {
        return receiverIban != null ? receiverIban.toUpperCase() : null;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban.toUpperCase();
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

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(TransferRequest.class))
            return false;
        TransferRequest other=(TransferRequest)o;

        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode(){
        String s = this.iban+this.amount +this.date;
        return s.hashCode();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
