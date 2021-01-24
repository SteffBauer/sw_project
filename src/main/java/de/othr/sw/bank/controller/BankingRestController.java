package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.TransferRequest;
import de.othr.sw.bank.service.AccountNotFoundException;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.InvalidTransferException;
import de.othr.sw.bank.service.NotEnoughMoneyException;
import de.othr.sw.bank.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banking")
public class BankingRestController {

    @Autowired
    private BankingServiceIF bankingServiceIF;
    @Autowired
    private AuthenticationUtils authenticationUtils;

    @RequestMapping("/accounts/{iban}")
    public ResponseEntity<Long> getAccountValue(@PathVariable("iban") String iban, @RequestHeader("access-token") String accessToken) {
        if(!authenticationUtils.isValidToken(accessToken))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return bankingServiceIF.getAccountValue(iban);
    }

    @PostMapping("/transfers/transfer")
    public ResponseEntity<TransferRequest> transferMoney(@RequestBody TransferRequest transferRequest, @RequestHeader("access-token") String accessToken) {
        if(!authenticationUtils.isValidToken(accessToken))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            return bankingServiceIF.transferMoney(transferRequest);
        } catch (NotEnoughMoneyException e) {
            return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InvalidTransferException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/transfers/mandate")
    public ResponseEntity<TransferRequest> mandateMoney(@RequestBody TransferRequest transferRequest, @RequestHeader("access-token") String accessToken) {
        if(!authenticationUtils.isValidToken(accessToken))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            return bankingServiceIF.mandateMoney(transferRequest);
        } catch (NotEnoughMoneyException e) {
            return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidTransferException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
