package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Privilege extends BaseEntity{
    private String privilegeName;
    @OneToMany(mappedBy = "privilege", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<PersonAuthority> personAuthorities = new HashSet<>();

    public Privilege() {
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(PrivilegeName privilegeName) {
        this.privilegeName = privilegeName.getPrivilege();
    }

    public Set<PersonAuthority> getPersonAuthorities() {
        return personAuthorities;
    }

    public void setPersonAuthorities(Set<PersonAuthority> personAuthorities) {
        this.personAuthorities = personAuthorities;
    }
}
