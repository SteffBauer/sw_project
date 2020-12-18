package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Transfer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransferRepositoryIF extends CrudRepository<Transfer,Long> {
    List<Transfer> findTransfersByPayerAccountIdAndReceiverAccountIdOrderByDateDesc(long idPayerAccount, long idReceiverAccount);

}
