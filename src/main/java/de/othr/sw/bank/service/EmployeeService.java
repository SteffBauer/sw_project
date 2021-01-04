package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.Customer;
import de.othr.sw.bank.entity.Employee;
import de.othr.sw.bank.entity.EmployeeDesignation;
import de.othr.sw.bank.repo.EmployeeRepositoryIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/employees")
@Qualifier("employeeUserDetailsService")
public class EmployeeService implements EmployeeServiceIF, UserDetailsService {

    @Autowired
    private EmployeeRepositoryIF employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getEmployeeByUsername(username)
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException("Customer with name " + username + " not found.");
                        }
                );
    }

    @Override
    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findDistinctByUsername(username);
    }

    @Override
    public Employee getEmployeeForCustomerSupport() {
        List<Employee> employees = employeeRepository.findEmployeeByDesignation(EmployeeDesignation.ACCOUNT_MANAGER.getDesignation());
        Optional<Employee> employee = employees.stream().min(Comparator.comparingInt(x -> x.getCustomers().size()));
        return employee.isPresent() ? employee.get() : null;
    }

    @Override
    public void removeCustomerFromEmployee(Customer customer) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(customer.getAttendant().getId());
        optionalEmployee.ifPresent(e -> {
                    e.removeCustomer(customer);
                    employeeRepository.save(e);
                }
        );
    }
}
