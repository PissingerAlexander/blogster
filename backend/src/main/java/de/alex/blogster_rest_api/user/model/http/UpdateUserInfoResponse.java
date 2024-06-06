package de.alex.blogster_rest_api.user.model.http;

import de.alex.blogster_rest_api.user.model.User;

public class UpdateUserInfoResponse extends CustomResponse<User> {

    public UpdateUserInfoResponse(User response) {
        super(response);
    }

    public UpdateUserInfoResponse(String error) {
        super(error);
    }
}
