package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Session;
import de.othr.sw.bank.entity.SessionRequest;
import de.othr.sw.bank.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("api/banking")
public class SessionService {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/session")
    public ResponseEntity<UUID> openSession(@RequestBody SessionRequest sessionRequest){
        Iterable<Account> accounts= accountRepository.findAccountByIban(sessionRequest.getIban());
        if(!accounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Session session = new Session(accounts.iterator().next());
        return new ResponseEntity<>(session.getSessionUuid(), HttpStatus.OK);
    }
}
