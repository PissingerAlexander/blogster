package de.alex.blogster_rest_api.comment.model.http.update_comment;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class UpdateCommentResponse extends CustomResponse<Comment> {
    public UpdateCommentResponse(Comment data) {
        super(data);
    }

    public UpdateCommentResponse(String error) {
        super(error);
    }
}
