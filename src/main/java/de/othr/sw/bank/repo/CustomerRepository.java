package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
