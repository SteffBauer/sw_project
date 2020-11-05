package de.othr.sw.bank.entity;

import java.util.Date;
import java.util.UUID;

public class TransferRequest {
    private UUID sessionUuid;
    private String surname;
    private String forename;
    private String iban;
    private double value;
    private Date date;

    public TransferRequest(){}

    public TransferRequest(UUID sessionUuid, String surname, String forename, String iban, double value, Date date) {
        this.sessionUuid = sessionUuid;
        this.surname = surname;
        this.forename = forename;
        this.iban = iban;
        this.value = value;
        this.date = date;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
        String s = this.iban+this.value+this.date;
        return s.hashCode();
    }
}
