package de.alex.blogster_rest_api.post.model;

import de.alex.blogster_rest_api.blog.model.Blog;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

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
    private String content;
    @NotNull
    private Date createdAt = new Date();

    public Post() {}

    public Post(

            @NotNull @Size(min = 4, max = 64) String postTitle,
            @NotNull Blog blog,
            @NotNull String content
    ) {
        this.postTitle = postTitle;
        this.blog = blog;
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

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public @NotNull Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Post title: " + postTitle + ", Blog name: " + blog.getBlogName() + ", Content: " + content;
    }
}
