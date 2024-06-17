package de.alex.blogster_rest_api.post.model.http.get_page;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.post.model.http.ResponseType.GetPageResponseType;

public class GetPageResponse extends CustomResponse<GetPageResponseType> {
    public GetPageResponse(GetPageResponseType data) {
        super(data);
    }

    public GetPageResponse(String error) {
        super(error);
    }
}
