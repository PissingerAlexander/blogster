package de.alex.blogster_rest_api.user.model.http.update_user_info;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserInfoRequest {
    @NotNull
    @Size(min = 3, max = 64)
    private String username;
    private String fullName;
    @NotNull
    private String mailAddress;

    public String getFullName() {
        return fullName;
    }

    public @NotNull @Size(min = 3, max = 64) String getUsername() {
        return username;
    }

    public @NotNull String getMailAddress() {
        return mailAddress;
    }
}
