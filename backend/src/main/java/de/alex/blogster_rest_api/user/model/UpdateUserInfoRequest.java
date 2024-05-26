package de.alex.blogster_rest_api.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserInfoRequest {
    private String fullName;
    @Size(min = 4, max = 64)
    @NotNull()
    private String username;
    @NotNull()
    private String mailAddress;

    public String getFullName() {
        return fullName;
    }

    public @Size(min = 4, max = 64) @NotNull() String getUsername() {
        return username;
    }

    public @NotNull() String getMailAddress() {
        return mailAddress;
    }
}
