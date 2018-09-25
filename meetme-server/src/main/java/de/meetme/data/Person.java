package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

/**
 * Plain data object which can be stored in the database or transfered as JSON.
 *
 * Never add additional logical to this class. By intent it only contains data.
 */
@Entity // Declare to dropwizard that this is a persisted object. A table will be created automatically.
public class Person extends PersistentObject {
    // create table person ( id int, name varchar(256), firstname varchar(256), email varchar(256))

    private String name;
    private String firstName;
    private String email;
    private String description;

    public Person() {
        // Needed by Jackson deserialization
        super(0);
    }

    public Person(long id, String firstName, String name, String email,String description){
        super(id);
        this.firstName = firstName;
        this.name = name;
        this.email = email;
        this.description = description;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getName() {
        return name;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getEmail() {
        return email;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
