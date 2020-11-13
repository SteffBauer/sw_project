package de.othr.sw.bank.entity;

public class Employee extends Customer {
    private double salary;
    private String designation;

    public Employee() { }

    public Employee(String forename, String surname, String username, String taxNumber, String password, double salary, String designation) {
        super(forename, surname, username, taxNumber, password);
        this.salary = salary;
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
