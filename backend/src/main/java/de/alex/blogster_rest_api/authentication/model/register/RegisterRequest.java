package de.alex.blogster_rest_api.authentication.model.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "{request.username.not.blank}")
    @Size(min = 3, max = 64)
    private String username;
    @NotBlank(message = "{request.password.not.blank}")
    @Size(min = 8)
    private String password;
    private String fullName;
    @Email(message = "{request.mailAddress.not.blank}")
    private String mailAddress;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMailAddress() {
        return mailAddress;
    }
}
