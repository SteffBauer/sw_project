package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Employee;

import java.util.Optional;

public interface EmployeeServiceIF {
    Optional<Employee> getEmployeeByUsername(String username);
    Employee getEmployeeForCustomerSupport();
}
