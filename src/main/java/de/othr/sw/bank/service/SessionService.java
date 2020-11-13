package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Session;
import de.othr.sw.bank.entity.SessionRequest;
import de.othr.sw.bank.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("api/banking")
public class SessionService {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/session")
    public ResponseEntity<UUID> openSession(@RequestBody SessionRequest sessionRequest){
        Iterable<Customer> customers= customerRepository.finCustomerByUsername(sessionRequest.getUsername());
        if(!customers.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Session session = new Session(customers.iterator().next());
        return new ResponseEntity<>(session.getUuid(), HttpStatus.OK);
    }
}
