package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepositoryIF extends PagingAndSortingRepository<Customer,Long> {
    Optional<Customer> findCustomerByUsername(String username);
    Optional<Customer> findCustomerByTaxNumber(String taxnumber);
    Page<Customer> findCustomerByForenameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String forename, String surname, String username, Pageable pageable);

}
