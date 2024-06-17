package de.alex.blogster_rest_api.blog.model.http.get_page;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;

public class GetBlogPageResponse extends CustomResponse<GetPage<Blog>> {
    public GetBlogPageResponse(GetPage<Blog> data) {
        super(data);
    }

    public GetBlogPageResponse(String error) {
        super(error);
    }
}
