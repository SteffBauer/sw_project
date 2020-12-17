package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface TransferRepositoryIF extends CrudRepository<Transfer,Long> {
}
