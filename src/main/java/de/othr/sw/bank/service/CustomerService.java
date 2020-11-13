package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.repo.AccountRepository;
import de.othr.sw.bank.repo.AddressRepository;
import de.othr.sw.bank.repo.CustomerRepository;
import de.othr.sw.bank.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("api/customers")
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountRepository accountRepository;

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

        Address address = newCustomer.getAddress();
        Iterable<Address> addresses = addressRepository.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(),address.getCity(),address.getZipCode(), address.getStreet(),address.getHouseNr());
        if(addresses.iterator().hasNext()){
            address = addresses.iterator().next();
        }
        else {
            address = addressRepository.save(address);
        }

        // Set the address of the customer
        newCustomer.setAddress(address);
        newCustomer = customerRepository.save(newCustomer);


        // Update residents for the address
        address.addResident(newCustomer);
        addressRepository.save(address);

        // Do not return the residents for the customer address
        newCustomer.getAddress().setResidents(null);
        newCustomer.setPassword(null);
        newCustomer.setPasswordHash(null);

        return new ResponseEntity<>(newCustomer,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") long cId){
        Optional<Customer> customer = customerRepository.findById(cId);

        if(customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Customer c = customer.get();
        c.getAddress().setResidents(null);
        c.setPassword(null);
        c.setPasswordHash(null);
        return new ResponseEntity<>(c,HttpStatus.OK);
    }

    @PutMapping("/{id}/createaccount")
    public ResponseEntity<Account> createAccount(@PathVariable("id") long cId){
        Optional<Customer> customer = customerRepository.findById(cId);

        if(customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account account = new Account(customer.get());
        account = accountRepository.save(account);
        account.createIban();
        account= accountRepository.save(account);

        account.setCustomer(null);

        return new ResponseEntity<>(account,HttpStatus.OK);
    }

}
