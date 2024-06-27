package de.alex.blogster_rest_api.authentication.model.login;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "{request.username.not.blank}")
    private String username;
    @NotBlank(message = "{request.password.not.blank}")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
