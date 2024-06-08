package de.alex.blogster_rest_api.blog.model.http.create_blog;

import jakarta.validation.constraints.NotNull;

public class CreateBlogRequest {
    @NotNull
    private String blogName;

    public @NotNull String getBlogName() {
        return blogName;
    }
}
