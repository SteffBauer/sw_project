package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;

public class InvalidTransferException extends Exception {

    private Account payerAccount;
    private Account receiverAccount;

    public InvalidTransferException(Account payerAccount, Account receiverAccount) {
        this.payerAccount=payerAccount;
        this.receiverAccount=receiverAccount;
    }

    public Account getPayerAccount() {
        return payerAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }
}