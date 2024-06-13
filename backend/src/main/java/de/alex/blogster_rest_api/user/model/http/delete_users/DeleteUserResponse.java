package de.alex.blogster_rest_api.user.model.http.delete_users;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class DeleteUserResponse extends CustomResponse<User> {
    public DeleteUserResponse(User data) {
        super(data);
    }

    public DeleteUserResponse(String error) {
        super(error);
    }
}
