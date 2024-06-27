package de.alex.blogster_rest_api.blog.model.http.create_blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateBlogRequest {
    @NotBlank(message = "{blogRequest.blogName.not.blank}")
    @Size(min = 4, max = 128)
    private String blogName;

    public @NotBlank String getBlogName() {
        return blogName;
    }
}
