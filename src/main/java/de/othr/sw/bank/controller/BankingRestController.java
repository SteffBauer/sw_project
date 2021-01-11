package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.TransferRequest;
import de.othr.sw.bank.service.AccountNotFoundException;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.InvalidTransferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banking")
public class BankingRestController {

    @Autowired
    private BankingServiceIF bankingServiceIF;

    @RequestMapping("/accounts")
    public ResponseEntity<Long> getAccountValue(@RequestBody AccountRequest accountRequest) {
        return bankingServiceIF.getAccountValue(accountRequest);
    }

    @PostMapping("/accounts/transfers/transfer")
    public ResponseEntity<TransferRequest> transferMoney(@RequestBody TransferRequest transferRequest) {
        try {
            return bankingServiceIF.transferMoney(transferRequest);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidTransferException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/accounts/transfers/mandate")
    public ResponseEntity<TransferRequest> mandateMoney(@RequestBody TransferRequest transferRequest) {
        try {
            return bankingServiceIF.mandateMoney(transferRequest);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidTransferException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
