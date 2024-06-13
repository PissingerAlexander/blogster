package de.alex.blogster_rest_api.post.model.http.get_post;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.post.model.Post;

public class GetPostResponse extends CustomResponse<Post> {
    public GetPostResponse(Post data) {
        super(data);
    }

    public GetPostResponse(String error) {
        super(error);
    }
}
