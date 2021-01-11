package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AddressRepositoryIF;
import de.othr.sw.bank.repo.CustomerRepositoryIF;
import de.othr.sw.bank.utils.DateUtils;
import de.othr.sw.bank.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Service
@Qualifier("customerUserDetailsService")
public class CustomerService implements CustomerServiceIF, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BankingServiceIF bankingService;
    @Autowired
    private EmployeeServiceIF employeeService;

    @Autowired
    private CustomerRepositoryIF customerRepository;
    @Autowired
    private AddressRepositoryIF addressRepository;


    @Override
    @Transactional
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) throws UsernameAlreadyInUseException, TaxNumberAlreadyRegisteredException, PersonTooYoungException {

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
        if (!customerRepository.findCustomerByUsername(newCustomer.getUsername()).isEmpty())
            throw new UsernameAlreadyInUseException("Username '" + newCustomer.getUsername() + "' already in use.");
        // Check if customer is already registered (taxnumber)
        if (!customerRepository.findCustomerByTaxNumber(newCustomer.getTaxNumber()).isEmpty())
            throw new TaxNumberAlreadyRegisteredException("Customer with taxnumber '" + newCustomer.getTaxNumber() + "' already registered.");
        // Check if customer is older than 12 years
        if (!DateUtils.isOldEnough(newCustomer.getBirthDate()))
            throw new PersonTooYoungException("Customer is too young. Customer has to be at least 12 years old");

        // Check if address already exists
        Address address = newCustomer.getAddress();
        Iterable<Address> addresses = addressRepository.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getHouseNr());
        if (addresses.iterator().hasNext())
            address = addresses.iterator().next();

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
        newCustomer = customerRepository.save(newCustomer);


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
        return customerRepository.findCustomerByUsername(username);
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> findCustomer(String taxNumber) {
        Optional<Customer> customer = customerRepository.findCustomerByTaxNumber(taxNumber);
        if (customer.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Customer>> queryCustomers(String queryString, Pageable pageable) {
        Page<Customer> customers = customerRepository.findCustomerByForenameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrUsernameContainingIgnoreCase(queryString,queryString,queryString,pageable);

        if(customers == null || customers.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest accountRequest) {
        return bankingService.createAccount(accountRequest);
    }

    @Override
    public ResponseEntity<Customer> deleteCustomerById(long cid) {
        Optional<Customer> customer = customerRepository.findById(cid);
        if (customer.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Customer c = customer.get();

        // Delete relation to employee
        employeeService.removeCustomerFromEmployee(c);


        c.setActive(false);
        c = customerRepository.save(c);
        return new ResponseEntity<Customer>(c, HttpStatus.OK);
    }

    @Override
    public List<Account> getActiveAccountsForUser(long id) {
        List<Account> accounts = customerRepository.findById(id).get().getActiveAccounts();
        return accounts;
    }

    @Override
    public ResponseEntity<Address> updateAddressForUser(long id, Address address) {
        ResponseEntity responseEntity = getCustomerById(id);
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Customer customer = (Customer) responseEntity.getBody();

        // Remove Customer from old address
        Address oldAddress = addressRepository.findById(customer.getAddress().getId()).orElse(null);
        if (oldAddress != null) {
            oldAddress.removeResident(customer);
            customer.setAddress(null);
        }


        // Check if address already exists
        Iterable<Address> addresses = addressRepository.findByCountryAndCityAndZipCodeAndStreetAndHouseNr(address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getHouseNr());
        if (addresses.iterator().hasNext())
            address = addresses.iterator().next();


        // Set the address of the customer
        customer.setAddress(address);
        address.addResident(customer);

        customerRepository.save(customer);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
