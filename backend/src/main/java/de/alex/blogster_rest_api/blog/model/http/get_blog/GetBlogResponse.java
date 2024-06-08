package de.alex.blogster_rest_api.blog.model.http.get_blog;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class GetBlogResponse extends CustomResponse<Blog> {
    public GetBlogResponse(Blog data) {
        super(data);
    }

    public GetBlogResponse(String error) {
        super(error);
    }
}
