package de.alex.blogster_rest_api.user.model.http;

import de.alex.blogster_rest_api.user.model.User;
import jakarta.validation.constraints.NotNull;

public class UpdateUserInfoResponse {
    @NotNull
    User user;
    @NotNull
    String accessToken;

    public UpdateUserInfoResponse(User user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }
}
