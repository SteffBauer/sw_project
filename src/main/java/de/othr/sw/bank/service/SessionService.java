package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Session;
import de.othr.sw.bank.entity.SessionRequest;
import de.othr.sw.bank.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("api/banking")
public class SessionService {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/session")
    public ResponseEntity<UUID> openSession(@RequestBody SessionRequest sessionRequest){
        Iterable<Account> accounts= accountRepository.findAccountByIban(sessionRequest.getIban());
        if(!accounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Session session = new Session(accounts.iterator().next());
        return new ResponseEntity<>(session.getUuid(), HttpStatus.OK);
    }
}
