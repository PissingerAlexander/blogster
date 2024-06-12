package de.alex.blogster_rest_api.blog.model.http.update_blog;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateBlogRequest {
    @NotNull
    private long id;
    @NotNull
    @Size(min = 4, max = 128)
    private String blogName;

    public @NotNull long getId() {
        return id;
    }

    public @NotNull @Size(min = 4, max = 128) String getBlogName() {
        return blogName;
    }
}
