package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceIF {


    List<Account> getAccountsForUser(long id);

    Optional<Customer> getCustomerByUsername(String username);

    ResponseEntity<Customer> findCustomer(String taxnumber);

    ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer);

    ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest accountRequest);
}