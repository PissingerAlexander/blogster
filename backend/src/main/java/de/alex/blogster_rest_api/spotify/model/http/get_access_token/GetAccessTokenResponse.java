package de.alex.blogster_rest_api.spotify.model.http.get_access_token;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.spotify.model.http.ResponseType.GetAccessTokenResponseType;

public class GetAccessTokenResponse extends CustomResponse<GetAccessTokenResponseType> {
    public GetAccessTokenResponse(GetAccessTokenResponseType data) {
        super(data);
    }

    public GetAccessTokenResponse(String error) {
        super(error);
    }
}
