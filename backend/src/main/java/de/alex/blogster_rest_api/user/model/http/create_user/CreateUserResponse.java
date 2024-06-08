package de.alex.blogster_rest_api.user.model.http.create_user;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class CreateUserResponse extends CustomResponse<User> {
    public CreateUserResponse(User data) {
        super(data);
    }

    public CreateUserResponse(String error) {
        super(error);
    }
}
