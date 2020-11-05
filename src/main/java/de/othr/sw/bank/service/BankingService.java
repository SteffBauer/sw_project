package de.othr.sw.bank.service;

import de.othr.sw.bank.repo.AccountRepository;
import de.othr.sw.bank.repo.AddressRepository;
import de.othr.sw.bank.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/banking")
public class BankingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountRepository accountRepository;
}
