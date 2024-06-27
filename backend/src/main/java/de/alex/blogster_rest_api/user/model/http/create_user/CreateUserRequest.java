package de.alex.blogster_rest_api.user.model.http.create_user;

import de.alex.blogster_rest_api.role.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull
    private Role role;
    @NotBlank(message = "{request.username.not.blank}")
    @Size(min = 3, max = 64)
    private String username;
    @NotBlank(message = "{request.password.not.blank}")
    @Size(min = 8)
    private String password;
    private String fullName;
    @Email(message = "{request.mailAddress.not.blank}")
    private String mailAddress;

    public @NotNull Role getRole() {
        return role;
    }
    public @NotBlank @Size(min = 3, max = 64) String getUsername() {
        return username;
    }
    public @NotBlank @Size(min = 8) String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
    public @Email String getMailAddress() {
        return mailAddress;
    }
}
