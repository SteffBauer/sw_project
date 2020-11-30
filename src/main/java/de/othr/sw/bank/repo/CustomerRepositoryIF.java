package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepositoryIF extends CrudRepository<Customer,Long> {
    List<Customer> findCustomerByUsername(String username);
}
