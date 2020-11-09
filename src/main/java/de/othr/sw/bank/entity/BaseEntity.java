package de.othr.sw.bank.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private long getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(BaseEntity.class))
            return false;
        BaseEntity other=(BaseEntity) o;

        return this.id==other.id;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.id);
    }

}

/*
@MappedSuperclass
GeneratedIdEntity
+ getID() : Long
* */
