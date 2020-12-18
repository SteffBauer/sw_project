package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AddressRepositoryIF;
import de.othr.sw.bank.repo.CustomerRepositoryIF;
import de.othr.sw.bank.repo.EmployeeRepositoryIF;
import de.othr.sw.bank.service.BankingServiceIF;
import de.othr.sw.bank.service.CustomerServiceIF;
import de.othr.sw.bank.utils.StringUtils;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("api/customers")
@Qualifier("customerUserDetailsService")
public class CustomerService implements CustomerServiceIF,UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private BankingServiceIF bankingServiceIF;

    @Autowired
    private CustomerRepositoryIF customerRepositoryIF;
    @Autowired
    private AddressRepositoryIF addressRepositoryIF;


    @Override
    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {

        if(StringUtils.isNullOrEmpty(newCustomer.getForename()) ||
            StringUtils.isNullOrEmpty(newCustomer.getSurname()) ||
                StringUtils.isNullOrEmpty(newCustomer.getUsername()) ||
            StringUtils.isNullOrEmpty(newCustomer.getPassword()) ||
            StringUtils.isNullOrEmpty(newCustomer.getTaxNumber()) ||
            newCustomer.getAddress() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newCustomer);
        }

        // todo check if username is already in use
        // todo check if customer is already registered (taxnumber)

        Address address = newCustomer.getAddress();
        Iterable<Address> addresses = addressRepositoryIF.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(),address.getCity(),address.getZipCode(), address.getStreet(),address.getHouseNr());
        if(addresses.iterator().hasNext()){
            address = addresses.iterator().next();
        }
        else {
            address = addressRepositoryIF.save(address);
        }

        // Set the address of the customer
        newCustomer.setAddress(address);
        // Set the password of the customer
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        newCustomer = customerRepositoryIF.save(newCustomer);


        // Update residents for the address
        address.addResident(newCustomer);
        addressRepositoryIF.save(address);

        // Do not return the residents for the customer address
        newCustomer.getAddress().setResidents(null);
        newCustomer.setPassword(null);

        return new ResponseEntity(newCustomer,HttpStatus.CREATED);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") long cId){
        Optional<Customer> customer = customerRepositoryIF.findById(cId);

        if(customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Customer c = customer.get();
        c.getAddress().setResidents(null);
        c.setPassword(null);
        return new ResponseEntity<>(c,HttpStatus.OK);
    }
*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = getCustomerByUsername(username)
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException("Customer with name " + username + " not found.");
                        }
                );
        return customer;
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username){
        return customerRepositoryIF.findDistinctByUsername(username);
    }

    @Override
    public ResponseEntity<Customer> findCustomer(String taxnumber) {
        //todo implement;
        throw new NotYetImplementedException();
    }

    @Override
    @PostMapping("/account")
    public ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest accountRequest){
        return bankingServiceIF.createAccount(accountRequest);
    }

    @Override
    public List<Account> getAccountsForUser(long id) {
        List<Account> accounts = customerRepositoryIF.findById(id).get().getAccounts();
        return accounts;
    }
}
