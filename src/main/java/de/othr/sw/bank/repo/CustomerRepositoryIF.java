package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryIF extends CrudRepository<Customer,Long> {
    List<Customer> findCustomerByUsername(String username);
    Optional<Customer> findDistinctByUsername(String username);
}
