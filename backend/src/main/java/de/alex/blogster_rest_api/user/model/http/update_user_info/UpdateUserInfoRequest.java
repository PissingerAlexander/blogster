package de.alex.blogster_rest_api.user.model.http.update_user_info;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserInfoRequest {
    @NotBlank(message = "{request.username.not.blank}")
    @Size(min = 3, max = 64)
    private String username;
    private String fullName;
    @Email(message = "{request.mailAddress.not.blank}")
    private String mailAddress;

    public String getFullName() {
        return fullName;
    }

    public @NotBlank @Size(min = 3, max = 64) String getUsername() {
        return username;
    }

    public @Email String getMailAddress() {
        return mailAddress;
    }
}
