package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.repo.EmployeeRepositoryIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("api/employees")
@Qualifier("employeeUserDetailsService")
public class EmployeeService implements EmployeeServiceIF, UserDetailsService {

    @Autowired
    private EmployeeRepositoryIF employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = getEmployeeByUsername(username)
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException("Customer with name " + username + " not found.");
                        }
                );
        return employee;
    }

    @Override
    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findDistinctByUsername(username);
    }
}
