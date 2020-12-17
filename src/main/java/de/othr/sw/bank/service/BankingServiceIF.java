package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.TransferRequest;
import org.springframework.http.ResponseEntity;

public interface BankingServiceIF {

    ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest);
    ResponseEntity<Long> getAccountValue(AccountRequest accountRequest);
    ResponseEntity<Long> transferMoney(TransferRequest transferRequest);
    ResponseEntity<Long> mandateMoney(TransferRequest transferRequest);

}
