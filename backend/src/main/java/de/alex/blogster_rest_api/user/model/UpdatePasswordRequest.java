package de.alex.blogster_rest_api.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {
    @NotNull
    @Size(min = 8)
    private String oldPassword;
    @NotNull
    @Size(min = 8)
    private String newPassword;

    public @NotNull @Size(min = 8) String getOldPassword() {
        return oldPassword;
    }

    public @NotNull @Size(min = 8) String getNewPassword() {
        return newPassword;
    }
}
