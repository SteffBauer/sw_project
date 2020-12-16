package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.AddressRepositoryIF;
import de.othr.sw.bank.repo.CustomerRepositoryIF;
import de.othr.sw.bank.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("api/customers")
@Qualifier("labresources")
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepositoryIF customerRepositoryIF;
    @Autowired
    private AddressRepositoryIF addressRepositoryIF;
    @Autowired
    // todo refactor usage in Banking Service
    private AccountRepositoryIF accountRepositoryIF;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<Customer> newCustomer(@RequestBody Customer newCustomer) {


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

        return new ResponseEntity<>(newCustomer,HttpStatus.OK);
    }

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

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Customer customer = customerRepositoryIF.findById(Long.parseLong((id)))
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException("Kunde mit Nr. " + id + " existiert nicht");
                        }
                );
        return customer;
    }

    @PutMapping("/{id}/createaccount")
    public ResponseEntity<Account> createAccount(@PathVariable("id") long cId){
        Optional<Customer> customer = customerRepositoryIF.findById(cId);

        if(customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account account = new Account(customer.get());
        account = accountRepositoryIF.save(account);
        account.createIban();
        account= accountRepositoryIF.save(account);

        account.setCustomer(null);

        return new ResponseEntity<>(account,HttpStatus.OK);
    }

}
