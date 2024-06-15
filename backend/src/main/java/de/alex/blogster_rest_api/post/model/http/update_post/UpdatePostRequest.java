package de.alex.blogster_rest_api.post.model.http.update_post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePostRequest {
    @NotNull
    private long id;
    @NotNull
    private long blogId;
    @NotNull
    @Size(min = 4, max = 64)
    private String postTitle;
    @NotNull
    @Size(max = 100000)
    private String content;

    @NotNull
    public long getId() {
        return id;
    }


    public @NotNull long getBlogId() {
        return blogId;
    }

    public @NotNull @Size(min = 4, max = 64) String getPostTitle() {
        return postTitle;
    }

    public @NotNull @Size(max = 100000) String getContent() {
        return content;
    }
}
