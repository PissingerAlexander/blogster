package de.alex.blogster_rest_api.blog.model.http.delete_blog;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;

public class DeleteBlogResponse extends CustomResponse<Blog> {
    public DeleteBlogResponse(Blog data) {
        super(data);
    }

    public DeleteBlogResponse(String error) {
        super(error);
    }
}
