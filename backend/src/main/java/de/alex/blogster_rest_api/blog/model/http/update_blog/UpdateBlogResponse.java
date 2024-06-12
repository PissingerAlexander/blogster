package de.alex.blogster_rest_api.blog.model.http.update_blog;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class UpdateBlogResponse extends CustomResponse<Blog> {
    public UpdateBlogResponse(Blog data) {
        super(data);
    }

    public UpdateBlogResponse(String error) {
        super(error);
    }
}
