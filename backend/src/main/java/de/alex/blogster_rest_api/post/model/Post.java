package de.alex.blogster_rest_api.post.model;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.spotify.model.http.ResponseType.Track;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(min = 4, max = 64)
    private String postTitle;
    @NotNull
    @ManyToOne
    private Blog blog;
    @NotBlank
    @Size(max = 100000)
    private String content;
    private final Date createdAt = new Date();
    @NotNull
    private String trackId;
    @NotNull
    private String trackName;

    public Post() {}

    public Post(
            @NotBlank @Size(min = 4, max = 64) String postTitle,
            @NotNull Blog blog,
            @NotBlank String content,
            @NotNull String trackId,
            @NotNull String trackName
    ) {
        this.postTitle = postTitle;
        this.blog = blog;
        this.content = content;
        this.trackId = trackId;
        this.trackName = trackName;
    }

    public long getId() {
        return id;
    }

    public @NotBlank @Size(min = 4, max = 64) String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(@NotBlank @Size(min = 4, max = 64) String postTitle) {
        this.postTitle = postTitle;
    }

    public @NotNull Blog getBlog() {
        return blog;
    }

    public void setBlog(@NotNull Blog blog) {
        this.blog = blog;
    }

    public @NotBlank @Size(max = 100000) String getContent() {
        return content;
    }

    public void setContent(@NotBlank @Size(max = 100000) String content) {
        this.content = content;
    }

    public @NotNull Date getCreatedAt() {
        return createdAt;
    }

    public @NotNull String getTrackId() {
        return trackId;
    }

    public void setTrackId(@NotNull String trackId) {
        this.trackId = trackId;
    }

    public @NotNull String getTrackName() {
        return trackName;
    }

    public void setTrackName(@NotNull String trackName) {
        this.trackName = trackName;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Post title: " + postTitle + ", Blog name: " + blog.getBlogName() + ", Content: " + content;
    }
}
