package de.alex.blogster_rest_api.post.model.http.delete_post;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.post.model.Post;

public class DeletePostResponse extends CustomResponse<Post> {
    public DeletePostResponse(Post data) {
        super(data);
    }

    public DeletePostResponse(String error) {
        super(error);
    }
}
