package de.alex.blogster_rest_api.blog.model.http.create_blog;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class CreateBlogResponse extends CustomResponse<Blog> {
    public CreateBlogResponse(Blog data) {
        super(data);
    }

    public CreateBlogResponse(String error) {
        super(error);
    }
}
