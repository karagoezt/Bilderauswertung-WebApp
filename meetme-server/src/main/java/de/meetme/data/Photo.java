package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Plain data object which can be stored in the database or transfered as JSON.
 *
 * Never add additional logical to this class. By intent it only contains data.
 */
@Entity // Declare to dropwizard that this is a persisted object. A table will be created automatically.
public class Photo extends PersistentObject{

    @ManyToOne
    private Person person;
    @Lob
    private byte[] picture;
    private String category;
    private int score;

    public Photo() {
        // Needed by Jackson deserialization
        super(0);
    }

    public Photo(long id, Person person, String category, int score, byte[] picture) {
        super(id);
        this.person = person;
        this.category = category;
        this.score = score;
        this.picture = picture;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public Person getPerson() {
        return person;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getCategory() {
        return category;
    }

    @JsonProperty(value = "0") // if the object is streamed as JSON id will be included
    public int getScore() {
        return score;
    }

    public byte[] getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "person=" + person +
                ", category='" + category + '\'' +
                ", score=" + score +
                '}';
    }
}
