package de.meetme.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Plain data object which can be stored in the database or transfered as JSON.
 *
 * Never add additional logical to this class. By intent it only contains data.
 */
@Entity
public class Photo extends PersistentObject{

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    @Column(nullable = false, unique = true)
    @Lob
    private byte[] picture;
    @Column(nullable = false)
    private String category;
    private int score;

    public Photo() {
        // Needed by Jackson deserialization
        super(0);
    }

    public Photo(User user, String category, int score, byte[] picture) {
        super(0);
        this.user = user;
        this.category = category;
        this.score = score;
        this.picture = picture;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public User getUser() {
        return user;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public String getCategory() {
        return category;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public int getScore() {
        return score;
    }

    @JsonProperty // if the object is streamed as JSON id will be included
    public byte[] getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "user=" + user +
                ", category='" + category + '\'' +
                ", score=" + score +
                '}';
    }
}
