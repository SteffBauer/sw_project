package de.othr.sw.bank.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

@Entity(name = "employee")
public class Employee extends Person {
    private long salary;
    private String designation;

    @OneToMany(mappedBy = "attendant", fetch = FetchType.EAGER)
    private List<Customer> customers;

    public Employee() {
    }

    public Employee(String forename, String surname, String username,
                    String password, Date birthDate, long salary,
                    String designation) {
        super(forename, surname, username, birthDate, password);
        this.salary = salary;
        this.designation = designation;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(PersonAuthority authority : this.personAuthorities) {
            authorities.add(new Authority(authority.getRight().getPrivilegeName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(this.customers);
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        if (!this.customers.contains(customer))
            this.customers.add(customer);
    }

    public void removeCustomer(Customer c){
        this.customers.remove(c);
    }
}
