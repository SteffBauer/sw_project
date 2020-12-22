package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AddressRepositoryIF;
import de.othr.sw.bank.repo.CustomerRepositoryIF;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("api/customers")
@Qualifier("customerUserDetailsService")
public class CustomerService implements CustomerServiceIF, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private BankingServiceIF bankingServiceIF;
    @Autowired
    private EmployeeServiceIF employeeService;

    @Autowired
    private CustomerRepositoryIF customerRepositoryIF;
    @Autowired
    private AddressRepositoryIF addressRepositoryIF;


    @Override
    @PostMapping()
    @Transactional
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) throws UsernameAlreadyInUserException, TaxNumberAlreadyRegisteredException {

        // Check required attributes
        if (StringUtils.isNullOrEmpty(newCustomer.getForename()) ||
                StringUtils.isNullOrEmpty(newCustomer.getSurname()) ||
                StringUtils.isNullOrEmpty(newCustomer.getUsername()) ||
                StringUtils.isNullOrEmpty(newCustomer.getPassword()) ||
                StringUtils.isNullOrEmpty(newCustomer.getTaxNumber()) ||
                newCustomer.getAddress() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newCustomer);
        }

        // Check if username is already in use
        if (!customerRepositoryIF.findCustomerByUsername(newCustomer.getUsername()).isEmpty())
            throw new UsernameAlreadyInUserException("Username '" + newCustomer.getUsername() + "' already in use.");
        // Check if customer is already registered (taxnumber)
        if (!customerRepositoryIF.findCustomerByTaxNumber(newCustomer.getTaxNumber()).isEmpty())
            throw new TaxNumberAlreadyRegisteredException("User with taxnumber '" + newCustomer.getTaxNumber() + "' already registered.");
        // todo User older than 12 years? -> Requirements

        // Check if address already exists
        Address address = newCustomer.getAddress();
        Iterable<Address> addresses = addressRepositoryIF.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getHouseNr());
        if (addresses.iterator().hasNext())
            address = addresses.iterator().next();
        else
            address = addressRepositoryIF.save(address);


        // Set the address of the customer
        newCustomer.setAddress(address);
        address.addResident(newCustomer);
        // Set the password of the customer
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        // Set an employee for the customer support
        Employee employee = employeeService.getEmployeeForCustomerSupport();
        if (employee != null) {
            newCustomer.setAttendant(employee);
            employee.addCustomer(newCustomer);
        }
        // Save the customer
        newCustomer = customerRepositoryIF.save(newCustomer);


        // Do not return the residents for the customer address
        newCustomer.getAddress().setResidents(null);

        return new ResponseEntity(newCustomer, HttpStatus.CREATED);
    }


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
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepositoryIF.findCustomerByUsername(username);
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(long id) {
        Optional<Customer> customer = customerRepositoryIF.findById(id);
        if (customer.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> findCustomer(String taxnumber) {
        //todo implement;
        throw new NotYetImplementedException();
    }

    @Override
    @PostMapping("/account")
    public ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest accountRequest) {
        return bankingServiceIF.createAccount(accountRequest);
    }

    @Override
    public ResponseEntity<Customer> deleteCustomerById(long cid) {
        Optional<Customer> customer = customerRepositoryIF.findById(cid);
        if (customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Customer c = customer.get();

        // Delete relation to employee
        employeeService.removeCustomerFromEmployee(c);


        customerRepositoryIF.delete(c);
        return new ResponseEntity<Customer>(c, HttpStatus.OK);
    }

    @Override
    public List<Account> getAccountsForUser(long id) {
        List<Account> accounts = customerRepositoryIF.findById(id).get().getAccounts();
        return accounts;
    }
}
