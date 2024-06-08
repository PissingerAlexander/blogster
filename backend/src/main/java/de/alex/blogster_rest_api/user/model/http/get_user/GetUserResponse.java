package de.alex.blogster_rest_api.user.model.http.get_user;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class GetUserResponse extends CustomResponse<User> {
    public GetUserResponse(User data) {
        super(data);
    }

    public GetUserResponse(String error) {
        super(error);
    }
}
