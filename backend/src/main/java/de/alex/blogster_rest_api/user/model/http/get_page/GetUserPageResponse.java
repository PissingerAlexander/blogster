package de.alex.blogster_rest_api.user.model.http.get_page;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;
import de.alex.blogster_rest_api.user.model.User;

public class GetUserPageResponse extends CustomResponse<GetPage<User>> {
    public GetUserPageResponse(GetPage<User> data) {
        super(data);
    }

    public GetUserPageResponse(String error) {
        super(error);
    }
}
