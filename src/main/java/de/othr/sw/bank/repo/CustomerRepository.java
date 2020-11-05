package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
