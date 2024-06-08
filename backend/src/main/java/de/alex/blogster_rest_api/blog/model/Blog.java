package de.alex.blogster_rest_api.blog.model;

import de.alex.blogster_rest_api.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Params:
 *  <ul>
 *      <li>id @GeneratedValue</li>
 *      <li>blogName @NotNull</li>
 *      <li>owner @NotNull</li>
 *  </ul>
 */
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 4, max = 128)
    private String blogName;
    //TODO: add posts
    @NotNull
    @ManyToOne
    private User owner;

    public Blog() {}

    public Blog(String blogName, User owner) {
        this.blogName = blogName;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 4, max = 128) String getBlogName() {
        return blogName;
    }

    public void setBlogName(@NotNull @Size(min = 4, max = 128) String blogName) {
        this.blogName = blogName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + blogName + ", Owner: " + owner;
    }
}