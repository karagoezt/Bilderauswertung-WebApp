package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Plain data object which can be stored in the database or transfered as JSON.
 *
 * Never add additional logical to this class. By intent it only contains data.
 */
@Entity// Declare to dropwizard that this is a persisted object. A table will be created automatically.
@Table(name ="users")
public class Person extends PersistentObject {

    @Column(name ="user_email", nullable =false, unique = true)
    private String email;
    @Column(name = "user_password", nullable =false)
    private String password;
    @Column(name= "user_name", nullable =false)
    private String name;
    @Column(name= "user_firstname", nullable =false)
    private String firstName;
    @Column(name= "user_description", length = 200)
    private String description;

    public Person() {
        // Needed by Jackson deserialization
        super(0);
    }

    public Person(long id, String firstName, String name, String email, String description, String password){
        super(id);
        this.email = email;
        this.password = password;
        this.name = name;
        this.firstName = firstName;
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

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getDescription() {
        return description;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getPassword() {
        return password;
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
