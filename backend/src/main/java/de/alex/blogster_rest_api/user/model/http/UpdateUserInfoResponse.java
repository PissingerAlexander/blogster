package de.alex.blogster_rest_api.user.model.http;

import de.alex.blogster_rest_api.http.model.response.CustomResponse;
import de.alex.blogster_rest_api.user.model.http.response_type.UpdateUserInfoResponseType;

public class UpdateUserInfoResponse extends CustomResponse<UpdateUserInfoResponseType> {

    public UpdateUserInfoResponse(UpdateUserInfoResponseType data) {
        super(data);
    }

    public UpdateUserInfoResponse(String error) {
        super(error);
    }
}
