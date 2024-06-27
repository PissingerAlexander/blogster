package de.alex.blogster_rest_api.comment.model;

import de.alex.blogster_rest_api.post.model.Post;
import de.alex.blogster_rest_api.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String comment;
    @NotNull
    @ManyToOne
    private User author;
    @NotNull
    @ManyToOne
    private Post post;
    private final Date createdAt = new Date();

    public Comment() {}

    public Comment(@NotBlank @Size(min = 1, max = 255) String comment, @NotNull User author, @NotNull Post post) {
        this.comment = comment;
        this.author = author;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank @Size(min = 1, max = 255) String getComment() {
        return comment;
    }

    public void setComment(@NotBlank @Size(min = 1, max = 255) String comment) {
        this.comment = comment;
    }

    public @NotNull User getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull User author) {
        this.author = author;
    }

    public @NotNull Post getPost() {
        return post;
    }

    public void setPost(@NotNull Post post) {
        this.post = post;
    }

    public @NotNull Date getCreatedAt() {
        return createdAt;
    }
}
