package de.othr.sw.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.othr.sw.bank.entity.Address;
import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Message;
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
        /*
        Iterable<Address> addresses = addressRepository.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(),address.getCity(),address.getZipCode(), address.getStreet(),address.getHouseNr());

        if(addresses.iterator().hasNext()){
            address = addresses.iterator().next();
        }
*/
         address = addressRepository.save(address);
         newCustomer.setAddress(address);


        // Dauerhaft speichern
        newCustomer = customerRepository.save(newCustomer);
        address.addResident(newCustomer);
        address= addressRepository.save(address);


        return ResponseEntity.status(HttpStatus.OK).body(newCustomer);
    }

    @GetMapping
    public Customer findCustomer(@RequestParam("customerId") long cId){
        Optional<Customer> customer = customerRepository.findById(cId);

        if(customer.isEmpty())
            return null;
        return customer.get();
    }

}
