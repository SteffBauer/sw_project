package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepositoryIF extends CrudRepository<Account,Long> {
    Account findDistinctByIban(String iban);
}
