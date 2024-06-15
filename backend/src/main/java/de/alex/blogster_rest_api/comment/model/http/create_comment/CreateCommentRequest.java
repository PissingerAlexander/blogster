package de.alex.blogster_rest_api.comment.model.http.create_comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCommentRequest {
    @NotNull
    private long postId;
    @NotNull
    @Size(min = 1, max = 255)
    private String comment;


    public @NotNull long getPostId() {
        return postId;
    }

    public @NotNull @Size(min = 1, max = 255) String getComment() {
        return comment;
    }
}
