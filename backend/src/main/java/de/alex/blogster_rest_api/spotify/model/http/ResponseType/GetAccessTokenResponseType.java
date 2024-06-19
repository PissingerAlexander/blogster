package de.alex.blogster_rest_api.spotify.model.http.ResponseType;

public record GetAccessTokenResponseType(String access_token, String token_type, int expires_in, String refresh_token, String scope) {
}
