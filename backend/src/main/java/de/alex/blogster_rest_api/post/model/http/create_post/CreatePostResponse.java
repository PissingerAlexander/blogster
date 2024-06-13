package de.alex.blogster_rest_api.post.model.http.create_post;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.post.model.Post;

public class CreatePostResponse extends CustomResponse<Post> {
    public CreatePostResponse(Post data) {
        super(data);
    }

    public CreatePostResponse(String error) {
        super(error);
    }
}
