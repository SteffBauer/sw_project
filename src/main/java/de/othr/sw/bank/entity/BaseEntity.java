package de.othr.sw.bank.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId(){
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
