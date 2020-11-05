package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {
}
