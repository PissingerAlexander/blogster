package de.alex.blogster_rest_api.post.model.http.update_post;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.post.model.Post;

public class UpdatePostResponse extends CustomResponse<Post> {
    public UpdatePostResponse(Post data) {
        super(data);
    }

    public UpdatePostResponse(String error) {
        super(error);
    }
}
