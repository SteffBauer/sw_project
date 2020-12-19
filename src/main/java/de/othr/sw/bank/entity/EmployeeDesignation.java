package de.othr.sw.bank.entity;

public enum EmployeeDesignation {
    ADMIN("admin"),
    ACCOUNT_MANAGER("account manager");

    private String designation;

    EmployeeDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }
}
