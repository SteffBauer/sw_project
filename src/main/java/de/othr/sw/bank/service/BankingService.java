package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Transfer;
import de.othr.sw.bank.entity.TransferRequest;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.TransferRepositoryIF;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/banking")
public class BankingService {


    @Autowired
    private AccountRepositoryIF accountRepositoryIF;
    @Autowired
    private TransferRepositoryIF transferRepositoryIF;


    @GetMapping("/value")
    public ResponseEntity<Long> getAccountValue(@RequestBody AccountRequest accountRequest){
        throw new NotYetImplementedException();
    }

    @GetMapping("/transfers")
    public ResponseEntity<Transfer> getTransfers(@RequestBody AccountRequest accountRequest){
        throw new NotYetImplementedException();
    }

    @PutMapping("/transfer")
    public ResponseEntity<Long> transferRequest(@RequestBody TransferRequest transferRequest){
        throw new NotYetImplementedException();
    }

    @PutMapping("/mandate")
    public ResponseEntity<Long> mandateRequest(@RequestBody TransferRequest transferRequest){
        throw new NotYetImplementedException();
    }
}
