package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class of all data objects in meetme to make sure they all use the database id in the same way
 */
@MappedSuperclass // This makes sure that the fields of this class are treated the same way as the fields of the derived class
public abstract class PersistentObject {
    @Id // tell Dropwizward that this is the database id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // the id is automatically generated
    private long id;

    public PersistentObject(long id) {
        this.id = id;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistentObject that = (PersistentObject) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
