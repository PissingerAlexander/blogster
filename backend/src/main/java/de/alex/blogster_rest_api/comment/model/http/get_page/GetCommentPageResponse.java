package de.alex.blogster_rest_api.comment.model.http.get_page;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;

public class GetCommentPageResponse extends CustomResponse<GetPage<Comment>> {
    public GetCommentPageResponse(GetPage<Comment> data) {
        super(data);
    }

    public GetCommentPageResponse(String error) {
        super(error);
    }
}
