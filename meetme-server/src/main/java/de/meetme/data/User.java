package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Plain data object which can be stored in the database or transfered as JSON.
 *
 */
@Entity
public class User extends PersistentObject {

    @Column(nullable =false, unique = true)
    private String email;
    @Column(nullable =false)
    private String password;
    @Column(nullable =false)
    private String lastname;
    @Column(nullable =false)
    private String firstname;
    @Column(length = 200)
    private String description;

    public User() {
        // Needed by Jackson deserialization
        super(0);
    }

    public User(String firstname, String name, String email, String description, String password){
        super(0);
        this.email = email;
        this.password = password;
        this.lastname = name;
        this.firstname = firstname;
        this.description = description;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getLastname() {
        return lastname;
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
        return "User{" +
                "id=" + getId() +
                ", lastname='" + lastname + '\'' +
                ", firstName='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
