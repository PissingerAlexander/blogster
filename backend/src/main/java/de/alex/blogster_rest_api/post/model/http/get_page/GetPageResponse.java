package de.alex.blogster_rest_api.post.model.http.get_page;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;
import de.alex.blogster_rest_api.post.model.Post;

public class GetPageResponse extends CustomResponse<GetPage<Post>> {
    public GetPageResponse(GetPage<Post> data) {
        super(data);
    }

    public GetPageResponse(String error) {
        super(error);
    }
}
