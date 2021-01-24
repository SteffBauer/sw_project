package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Transfer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankingServiceIF extends BankingServiceExternalIF {

    ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest);

    ResponseEntity<Long> getAccountValue(String iban);

    ResponseEntity<List<Transfer>> getTransfersByAccountId(long id);

    ResponseEntity<Account> getAccountById(long id);

    ResponseEntity<Account> deleteAccount(long id);

}
