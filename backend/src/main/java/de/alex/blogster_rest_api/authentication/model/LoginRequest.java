package de.alex.blogster_rest_api.authentication.model;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
