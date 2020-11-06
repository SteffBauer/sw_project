package de.othr.sw.bank.entity;

import java.util.Date;
import java.util.UUID;

public class TransferRequest {
    private UUID sessionUuid;
    private String surname;
    private String forename;
    private String iban;
    private double amount;
    private Date date;
    private String description;

    public TransferRequest(){}

    public TransferRequest(UUID sessionUuid, String surname, String forename, String iban, double amount, Date date, String description) {
        this.sessionUuid = sessionUuid;
        this.surname = surname;
        this.forename = forename;
        this.iban = iban;
        this.amount = amount;
        this.date = date;
        this.description= description;
    }

    public UUID getSessionUuid() {
        return sessionUuid;
    }

    public void setSessionUuid(UUID sessionUuid) {
        this.sessionUuid = sessionUuid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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
