package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.TransferRepositoryIF;
import de.othr.sw.bank.utils.DateUtils;
import de.othr.sw.bank.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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


            // Future calls into the "SCHUFA"-System possible

            Account account = new Account(customer.get());

            account = accountRepository.save(account);
            account.createIban();
            account = accountRepository.save(account);

            accountRequest.setIban(account.getIban());

            return new ResponseEntity<>(accountRequest, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);

            return new ResponseEntity<>(accountRequest, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Long> getAccountValue(AccountRequest accountRequest) {
        if (accountRequest == null || StringUtils.isNullOrEmpty(accountRequest.getIban()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account account = accountRepository.findDistinctByIbanAAndActiveIsTrue(accountRequest.getIban());
        if (account == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<TransferRequest> transferMoney(TransferRequest transferRequest) throws AccountNotFoundException {
        return makeTransfer(transferRequest, false);
    }

    @Override
    public ResponseEntity<TransferRequest> mandateMoney(TransferRequest transferRequest) throws AccountNotFoundException {
        return makeTransfer(transferRequest, true);
    }

    @Override
    public ResponseEntity<Account> getAccountById(long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isEmpty())
            return new ResponseEntity<>(account.get(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Account> deleteAccount(long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);


        if (optionalAccount.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Account account = optionalAccount.get();

        account.setActive(false);
        account = accountRepository.save(account);

        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Transfer>> getTransfersByAccountId(long id) {
        try {
            List<Transfer> transfers = transferRepository.findTransfersByPayerAccountIdOrReceiverAccountIdOrderByDateCreatedDescDateDesc(id, id);
            return new ResponseEntity<>(transfers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private ResponseEntity<TransferRequest> makeTransfer(TransferRequest transferRequest, boolean isMandated) throws AccountNotFoundException {
        if (StringUtils.isNullOrEmpty(transferRequest.getReceiverSurname()) ||
                StringUtils.isNullOrEmpty(transferRequest.getReceiverForename()) ||
                StringUtils.isNullOrEmpty(transferRequest.getReceiverIban()) ||
                StringUtils.isNullOrEmpty(transferRequest.getIban()) ||
                Long.valueOf(0).equals(transferRequest.getAmount()) ||
                DateUtils.isBefore(transferRequest.getDate(), new Date())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account payerAccount = isMandated ? accountRepository.findDistinctByIbanAAndActiveIsTrue(transferRequest.getReceiverIban()) : accountRepository.findDistinctByIbanAAndActiveIsTrue(transferRequest.getIban());
        Account receiverAccount = isMandated ? accountRepository.findDistinctByIbanAAndActiveIsTrue(transferRequest.getIban()) : accountRepository.findDistinctByIbanAAndActiveIsTrue(transferRequest.getReceiverIban());

        if (payerAccount == null)
            throw new AccountNotFoundException(transferRequest.getIban());
        if (receiverAccount == null)
            throw new AccountNotFoundException(transferRequest.getReceiverIban());


        Transfer transfer = new Transfer(transferRequest.getDate(), transferRequest.getAmount(), transferRequest.getDescription(), payerAccount, receiverAccount);
        transferRepository.save(transfer);

        payerAccount.setBalance(payerAccount.getBalance() - transferRequest.getAmount());
        accountRepository.save(payerAccount);
        receiverAccount.setBalance(receiverAccount.getBalance() + transferRequest.getAmount());
        accountRepository.save(receiverAccount);

        return new ResponseEntity<>(transferRequest, HttpStatus.CREATED);
    }


}
