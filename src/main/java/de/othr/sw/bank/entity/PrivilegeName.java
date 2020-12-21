package de.othr.sw.bank.entity;

public enum PrivilegeName {
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    ROLE("ROLE_USER");

    private String privilege;

    PrivilegeName(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege() {
        return privilege;
    }
}
