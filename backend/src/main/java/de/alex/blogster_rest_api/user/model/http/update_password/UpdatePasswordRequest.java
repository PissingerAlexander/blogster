package de.alex.blogster_rest_api.user.model.http.update_password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {
    @NotBlank(message = "{request.password.not.blank}")
    @Size(min = 8)
    private String oldPassword;
    @NotBlank(message = "{request.password.not.blank}")
    @Size(min = 8)
    private String newPassword;

    public @NotBlank @Size(min = 8) String getOldPassword() {
        return oldPassword;
    }

    public @NotBlank @Size(min = 8) String getNewPassword() {
        return newPassword;
    }
}
