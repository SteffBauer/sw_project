package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account,Long> {
    List<Account> findAccountByIban(String iban);
}
