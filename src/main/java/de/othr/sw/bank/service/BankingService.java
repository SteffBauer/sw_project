package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.TransferRequest;
import de.othr.sw.bank.repo.AccountRepository;
import de.othr.sw.bank.repo.AddressRepository;
import de.othr.sw.bank.repo.CustomerRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("api/banking")
public class BankingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/account/value")
    public ResponseEntity<Double> getAccountValue(@RequestBody AccountRequest accountRequest){
        throw new NotYetImplementedException();
    }

    @GetMapping("/account/transfers")
    public ResponseEntity<Double> getTransfers(@RequestBody AccountRequest accountRequest){
        throw new NotYetImplementedException();
    }

    @PutMapping("/account/transfer")
    public ResponseEntity<Double> transferRequest(@RequestBody TransferRequest transferRequest){
        throw new NotYetImplementedException();
    }

    @PutMapping("/account/mandate")
    public ResponseEntity<Double> mandateRequest(@RequestBody TransferRequest transferRequest){
        throw new NotYetImplementedException();
    }
}
