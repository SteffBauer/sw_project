package de.othr.sw.bank.entity;

import javax.persistence.*;

@Entity
public class PersonAuthority extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    @ManyToOne(fetch = FetchType.EAGER)
    private Privilege privilege;

    public PersonAuthority(Person person, Privilege privilege) {
        this.person = person;
        this.privilege = privilege;
    }

    public PersonAuthority() {

    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Privilege getRight() {
        return privilege;
    }

    public void setRight(Privilege recht) {
        this.privilege = recht;
    }
}
