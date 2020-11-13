package de.othr.sw.bank.entity;

import java.util.UUID;

public class AccountRequest {

    private UUID uuid;
    private String username;
    private String iban;

    public AccountRequest() {}

    public AccountRequest(UUID uuid, String username,String iban) {
        this.uuid = uuid;
        this.username = username;
        this.iban = iban;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(SessionRequest.class))
            return false;
        SessionRequest other=(SessionRequest)o;

        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode(){
        String s = this.username +this.uuid;
        return s.hashCode();
    }
}
