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
    @JoinColumn(name="payer_account_id")
    private Account payerAccount;

    @ManyToOne
    @JoinColumn(name="receiver_account_id")
    private Account receiverAccount;
}
