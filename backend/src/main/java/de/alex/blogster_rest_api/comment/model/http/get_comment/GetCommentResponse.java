package de.alex.blogster_rest_api.comment.model.http.get_comment;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class GetCommentResponse extends CustomResponse<Comment> {
    public GetCommentResponse(Comment data) {
        super(data);
    }

    public GetCommentResponse(String error) {
        super(error);
    }
}
