package de.alex.blogster_rest_api.blog.model;

import de.alex.blogster_rest_api.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

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
    @NotBlank
    @Size(min = 4, max = 128)
    private String blogName;
    @NotNull
    @ManyToOne
    private User owner;
    private final Date createdAt = new Date();

    public Blog() {}

    public Blog(
            @NotBlank String blogName,
            @NotNull User owner
    ) {
        this.blogName = blogName;
        this.owner = owner;
    }

    public @NotNull long getId() {
        return id;
    }

    public void setId(@NotNull long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 4, max = 128) String getBlogName() {
        return blogName;
    }

    public void setBlogName(@NotBlank @Size(min = 4, max = 128) String blogName) {
        this.blogName = blogName;
    }

    public @NotNull User getOwner() {
        return owner;
    }

    public void setOwner(@NotNull User owner) {
        this.owner = owner;
    }

    public @NotNull Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Blog name: " + blogName + ", Owner: " + owner;
    }
}