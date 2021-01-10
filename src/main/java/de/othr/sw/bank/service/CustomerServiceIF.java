package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.AccountRequest;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceIF {


    List<Account> getActiveAccountsForUser(long id);

    ResponseEntity<Address> updateAddressForUser(long id, Address address);

    Optional<Customer> getCustomerByUsername(String username);

    ResponseEntity<Customer> getCustomerById(long id);

    ResponseEntity<Customer> findCustomer(String taxNumber);

    ResponseEntity<List<Customer>> queryCustomers(String queryString, Pageable pageable);

    ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) throws UsernameAlreadyInUseException, TaxNumberAlreadyRegisteredException, PersonTooYoungException;

    ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest accountRequest);

    ResponseEntity<Customer> deleteCustomerById(long cid);
}
