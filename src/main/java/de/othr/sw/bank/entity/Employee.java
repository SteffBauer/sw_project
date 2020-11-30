package de.othr.sw.bank.entity;

import java.util.Date;

public class Employee extends Person {
    private long salary;
    private String designation;

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
}
