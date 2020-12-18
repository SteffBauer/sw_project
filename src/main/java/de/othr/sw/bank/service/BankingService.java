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

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/banking")
public class BankingService implements BankingServiceIF {
    @Autowired
    private CustomerServiceIF customerServiceIF;


    @Autowired
    private AccountRepositoryIF accountRepository;
    @Autowired
    private TransferRepositoryIF transferRepository;

    @Override
    public ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest) {
        try {
            Optional<Customer> customer = customerServiceIF.getCustomerByUsername(accountRequest.getUsername());

            if (customer.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Account account = new Account(customer.get());

            account = accountRepository.save(account);
            account.createIban();
            account = accountRepository.save(account);

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

    @Override
    public ResponseEntity<Account> getAccountById(long id) {
        Optional<Account> account = accountRepository.findById(id);

        if(account.isEmpty())
            return new ResponseEntity(account.get(),HttpStatus.NOT_FOUND);

        return new ResponseEntity(account.get(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Transfer>> getTransfersByAccountId(long id) {
        try {
            List<Transfer> transfers = transferRepository.findTransfersByPayerAccountIdAndReceiverAccountIdOrderByDateDesc(id,id);
            return new ResponseEntity(transfers, HttpStatus.OK);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
