package de.alex.blogster_rest_api.post.model.http.create_post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {
    @NotNull
    private long blogId;
    @NotBlank(message = "{postRequest.postTitle.not.blank}")
    @Size(min = 4, max = 64)
    private String postTitle;
    @NotBlank(message = "{postRequest.content.not.blank}")
    @Size(max = 100000)
    private String content;
    @NotNull
    private String trackId;
    @NotNull
    private String trackName;

    public @NotNull long getBlogId() {
        return blogId;
    }

    public @NotBlank String getPostTitle() {
        return postTitle;
    }

    public @NotBlank @Size(max = 100000) String getContent() {
        return content;
    }

    public @NotNull String getTrackId() {
        return trackId;
    }

    public @NotNull String getTrackName() {
        return trackName;
    }
}
