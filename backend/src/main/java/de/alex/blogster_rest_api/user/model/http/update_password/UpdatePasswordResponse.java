package de.alex.blogster_rest_api.user.model.http.update_password;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class UpdatePasswordResponse extends CustomResponse<User> {
    public UpdatePasswordResponse(User data) {
        super(data);
    }

    public UpdatePasswordResponse(String error) {
        super(error);
    }
}
