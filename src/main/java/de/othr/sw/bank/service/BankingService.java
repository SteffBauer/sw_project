package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.TransferRepositoryIF;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("api/banking")
public class BankingService implements BankingServiceIF {
    @Autowired
    private CustomerServiceIF customerServiceIF;


    @Autowired
    private AccountRepositoryIF accountRepositoryIF;
    @Autowired
    private TransferRepositoryIF transferRepositoryIF;

    @Override
    public ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest) {
        try {
            Optional<Customer> customer = customerServiceIF.getCustomerByUsername(accountRequest.getUsername());

            if (customer.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Account account = new Account(customer.get());

            account = accountRepositoryIF.save(account);
            account.createIban();
            account = accountRepositoryIF.save(account);

            accountRequest.setIban(account.getIban());

            return new ResponseEntity(accountRequest, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);

            return new ResponseEntity(accountRequest, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Long> getAccountValue(AccountRequest accountRequest) {
        throw new NotYetImplementedException();
    }

    @Override
    public ResponseEntity<Long> transferMoney(TransferRequest transferRequest) {
        throw new NotYetImplementedException();
    }

    @Override
    public ResponseEntity<Long> mandateMoney(TransferRequest transferRequest) {
        throw new NotYetImplementedException();
    }


}
