package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity
public class Session implements Serializable {

    @Id
    @GeneratedValue
    private long sessionId;
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private Date date;

    public Session(){}

    public Session(Account account) {
        this.date = new Date();
        this.uuid =UUID.randomUUID();
        this.account = account;
    }

    public long getSessionId() {
        return sessionId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account iban) {
        this.account = iban;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(Session.class))
            return false;
        Session other=(Session) o;

        return this.sessionId==other.sessionId;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.sessionId);
    }
}
