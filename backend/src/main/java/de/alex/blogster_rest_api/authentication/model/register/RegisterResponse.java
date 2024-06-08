package de.alex.blogster_rest_api.authentication.model.register;


import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.User;

public class RegisterResponse extends CustomResponse<User> {
    public RegisterResponse(String error) {
        super(error);
    }

    public RegisterResponse(User data) {
        super(data);
    }
}
