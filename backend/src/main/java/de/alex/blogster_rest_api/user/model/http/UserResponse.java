package de.alex.blogster_rest_api.user.model.http;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class UserResponse extends CustomResponse<User> {
    public UserResponse(User data) {
        super(data);
    }

    public UserResponse(String error) {
        super(error);
    }
}
