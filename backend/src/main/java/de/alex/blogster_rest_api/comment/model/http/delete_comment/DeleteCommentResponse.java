package de.alex.blogster_rest_api.comment.model.http.delete_comment;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class DeleteCommentResponse extends CustomResponse<Comment> {
    public DeleteCommentResponse(Comment data) {
        super(data);
    }

    public DeleteCommentResponse(String error) {
        super(error);
    }
}
