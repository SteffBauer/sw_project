package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepositoryIF extends CrudRepository<Customer,Long> {
    Optional<Customer> findDistinctByUsername(String username);
}
