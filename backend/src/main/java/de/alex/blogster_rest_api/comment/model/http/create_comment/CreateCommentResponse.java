package de.alex.blogster_rest_api.comment.model.http.create_comment;


import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class CreateCommentResponse extends CustomResponse<Comment> {
    public CreateCommentResponse(Comment data) {
        super(data);
    }

    public CreateCommentResponse(String error) {
        super(error);
    }
}
