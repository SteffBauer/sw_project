package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity
public class Session extends BaseEntity implements Serializable {
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    private Date date;

    public Session(){}

    public Session(Customer customer) {
        this.date = new Date();
        this.uuid =UUID.randomUUID();
        this.customer = customer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
