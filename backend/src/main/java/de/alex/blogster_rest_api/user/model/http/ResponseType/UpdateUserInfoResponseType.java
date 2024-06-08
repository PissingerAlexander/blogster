package de.alex.blogster_rest_api.user.model.http.ResponseType;

import de.alex.blogster_rest_api.user.model.User;

public record UpdateUserInfoResponseType(User user, String accessToken) {
}
