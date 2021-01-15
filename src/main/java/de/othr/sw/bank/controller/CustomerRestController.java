package de.othr.sw.bank.controller;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerServiceIF customerService;
    @Autowired
    private AuthenticationUtils authenticationUtils;

    @GetMapping("/{taxNumber}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("taxNumber") String taxNumber, @RequestHeader("access-token") String accessToken) {
        if(!authenticationUtils.isValidToken(accessToken))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return customerService.findCustomer(taxNumber);
    }
}
