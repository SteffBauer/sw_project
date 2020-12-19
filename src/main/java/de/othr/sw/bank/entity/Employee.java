package de.othr.sw.bank.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "employee")
public class Employee extends Person {
    private long salary;
    private String designation;

    @OneToMany(mappedBy="attendant")
    private List<Customer> customers;

    public Employee() { }

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
        return null;
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

}
