package de.alex.blogster_rest_api.post.model.http.create_post;

import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {
    @NotNull
    private long blogId;
    @NotNull
    private String postTitle;
    @NotNull
    private String content;

    public @NotNull long getBlogId() {
        return blogId;
    }

    public @NotNull String getPostTitle() {
        return postTitle;
    }

    public @NotNull String getContent() {
        return content;
    }
}
