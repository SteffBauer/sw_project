package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Transfer;
import de.othr.sw.bank.entity.TransferRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankingServiceIF {

    ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest);
    ResponseEntity<Long> getAccountValue(AccountRequest accountRequest);
    ResponseEntity<TransferRequest> transferMoney(TransferRequest transferRequest) throws AccountNotFoundException;
    ResponseEntity<TransferRequest> mandateMoney(TransferRequest transferRequest) throws AccountNotFoundException;
    ResponseEntity<Account> getAccountById(long id);
    ResponseEntity<List<Transfer>> getTransfersByAccountId(long id);

}
