package de.othr.sw.bank.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Privilege extends BaseEntity{
    private String rightName;
    @OneToMany(mappedBy = "privilege", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<PersonAuthority> personAuthorities = new HashSet<>();

    public Privilege() {
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Set<PersonAuthority> getPersonAuthorities() {
        return personAuthorities;
    }

    public void setPersonAuthorities(Set<PersonAuthority> personAuthorities) {
        this.personAuthorities = personAuthorities;
    }
}
