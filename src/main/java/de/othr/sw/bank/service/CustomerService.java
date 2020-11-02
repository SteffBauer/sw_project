package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public Customer registerCustomer(/* @RequestParam(value = "name", defaultValue = "World") String name*/) {

        // Customer erzeugen, prüfen, ...
        Customer customer =  new Customer(1234, "Max","Muster", "IN");

        // Dauerhaft speichern
        customer = customerRepository.save(customer);

        return customer;
    }

}
