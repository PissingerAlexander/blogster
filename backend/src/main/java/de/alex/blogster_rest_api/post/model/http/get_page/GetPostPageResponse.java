package de.alex.blogster_rest_api.post.model.http.get_page;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;
import de.alex.blogster_rest_api.post.model.Post;

public class GetPostPageResponse extends CustomResponse<GetPage<Post>> {
    public GetPostPageResponse(GetPage<Post> data) {
        super(data);
    }

    public GetPostPageResponse(String error) {
        super(error);
    }
}
