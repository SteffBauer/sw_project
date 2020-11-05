package de.othr.sw.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.othr.sw.bank.entity.Account;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Message;
import de.othr.sw.bank.repo.AccountRepository;
import de.othr.sw.bank.repo.AddressRepository;
import de.othr.sw.bank.repo.CustomerRepository;
import de.othr.sw.bank.utils.StringUtils;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Optional;

@RestController
public class CustomerService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("api/customers")
    public ResponseEntity<Customer> newCustomer(@RequestBody Customer newCustomer) {


        if(StringUtils.isNullOrEmpty(newCustomer.getForename()) ||
            StringUtils.isNullOrEmpty(newCustomer.getSurname()) ||
            StringUtils.isNullOrEmpty(newCustomer.getPasswordHash()) ||
            StringUtils.isNullOrEmpty(newCustomer.getTaxNumber()) ||
            newCustomer.getAddress() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newCustomer);
        }

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

        return new ResponseEntity(newCustomer,HttpStatus.OK);
    }

    @GetMapping("api/customers")
    public Customer findCustomer(@RequestParam("customerId") long cId){
        Optional<Customer> customer = customerRepository.findById(cId);

        if(customer.isEmpty())
            return null;
        return customer.get();
    }

    @PutMapping
    public Account createAccount(@PathVariable() long cId){
        Optional<Customer> customer = customerRepository.findById(cId);

        if(customer.isEmpty())
            return null;

        Account account = new Account(customer.get());
        account = accountRepository.save(account);

        return account;
    }

}
