package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.*;
import de.othr.sw.bank.repo.AccountRepositoryIF;
import de.othr.sw.bank.repo.TransferRepositoryIF;
import de.othr.sw.bank.utils.DateUtils;
import de.othr.sw.bank.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BankingService implements BankingServiceIF {

    @Autowired
    private CustomerServiceIF customerService;
    @Autowired
    private AccountRepositoryIF accountRepository;
    @Autowired
    private TransferRepositoryIF transferRepository;

    @Override
    public ResponseEntity<AccountRequest> createAccount(AccountRequest accountRequest) {
        try {
            Optional<Customer> customer = customerService.getCustomerByUsername(accountRequest.getUsername());

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
    public ResponseEntity<Long> getAccountValue(String iban) {
        if (StringUtils.isNullOrEmpty(iban))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account account = accountRepository.findDistinctByIbanAndActiveIsTrue(iban);
        if (account == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<TransferRequest> transferMoney(TransferRequest transferRequest) throws AccountNotFoundException, InvalidTransferException {
        return makeTransfer(transferRequest, false);
    }

    @Override
    public ResponseEntity<TransferRequest> mandateMoney(TransferRequest transferRequest) throws AccountNotFoundException, InvalidTransferException {
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


    private ResponseEntity<TransferRequest> makeTransfer(TransferRequest transferRequest, boolean isMandated) throws AccountNotFoundException, InvalidTransferException {
        if (StringUtils.isNullOrEmpty(transferRequest.getReceiverSurname()) ||
                StringUtils.isNullOrEmpty(transferRequest.getReceiverForename()) ||
                StringUtils.isNullOrEmpty(transferRequest.getReceiverIban()) ||
                StringUtils.isNullOrEmpty(transferRequest.getIban()) ||
                transferRequest.getAmount() <= 0L ||
                DateUtils.isBefore(transferRequest.getDate(), new Date())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account payerAccount = isMandated ? accountRepository.findDistinctByIbanAndActiveIsTrue(transferRequest.getReceiverIban()) : accountRepository.findDistinctByIbanAndActiveIsTrue(transferRequest.getIban());
        Account receiverAccount = isMandated ? accountRepository.findDistinctByIbanAndActiveIsTrue(transferRequest.getIban()) : accountRepository.findDistinctByIbanAndActiveIsTrue(transferRequest.getReceiverIban());

        if (payerAccount == null)
            throw new AccountNotFoundException(transferRequest.getIban());
        if (receiverAccount == null)
            throw new AccountNotFoundException(transferRequest.getReceiverIban());
        if(payerAccount.equals(receiverAccount))
            throw new InvalidTransferException(payerAccount,receiverAccount);


        Transfer transfer = new Transfer(transferRequest.getDate(), transferRequest.getAmount(), transferRequest.getDescription(), payerAccount, receiverAccount);
        transferRepository.save(transfer);

        payerAccount.setBalance(payerAccount.getBalance() - transferRequest.getAmount());
        accountRepository.save(payerAccount);
        receiverAccount.setBalance(receiverAccount.getBalance() + transferRequest.getAmount());
        accountRepository.save(receiverAccount);

        return new ResponseEntity<>(transferRequest, HttpStatus.CREATED);
    }


}
