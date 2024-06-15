package de.alex.blogster_rest_api.comment.model.http.delete_comment;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class DeletePostResponse extends CustomResponse<Comment> {
    public DeletePostResponse(Comment data) {
        super(data);
    }   

    public DeletePostResponse(String error) {
        super(error);
    }
}
