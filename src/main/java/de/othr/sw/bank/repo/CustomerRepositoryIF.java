package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryIF extends PagingAndSortingRepository<Customer,Long> {
    Optional<Customer> findCustomerByUsername(String username);
    Optional<Customer> findCustomerByTaxNumber(String taxnumber);
    List<Customer> findCustomerByForenameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String forename, String surname, String username, Pageable pageable);

}
