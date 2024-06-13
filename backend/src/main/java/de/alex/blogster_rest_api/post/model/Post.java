package de.alex.blogster_rest_api.post.model;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Params:
 *  <ul>
 *      <li>id @GeneratedValue</li>
 *      <li>postTitle @NotNull</li>
 *      <li>blog @NotNull</li>
 *      <li>owner @NotNull</li>
 *      <li>content</li>
 *  </ul>
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 4, max = 64)
    private String postTitle;
    @NotNull
    @ManyToOne
    private Blog blog;
    @NotNull
    @ManyToOne
    private User user;
    @NotNull
    private String content;

    public Post() {}

    public Post(

            @NotNull @Size(min = 4, max = 64) String postTitle,
            @NotNull Blog blog,
            @NotNull User user,
            @NotNull String content
    ) {
        this.postTitle = postTitle;
        this.blog = blog;
        this.user = user;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public @NotNull @Size(min = 4, max = 64) String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(@NotNull @Size(min = 4, max = 64) String postTitle) {
        this.postTitle = postTitle;
    }

    public @NotNull Blog getBlog() {
        return blog;
    }

    public void setBlog(@NotNull Blog blog) {
        this.blog = blog;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }
}