package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepositoryIF extends CrudRepository<Employee,Long> {
    Optional<Employee> findDistinctByUsername(String username);
}
