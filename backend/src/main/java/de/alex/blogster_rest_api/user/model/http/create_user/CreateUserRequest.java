package de.alex.blogster_rest_api.user.model.http.create_user;

import de.alex.blogster_rest_api.role.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull
    private Role role;
    @NotNull
    @Size(min = 3, max = 64)
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
    private String fullName;
    @NotNull
    private String mailAddress;

    public @NotNull Role getRole() {
        return role;
    }
    public @NotNull @Size(min = 3, max = 64) String getUsername() {
        return username;
    }
    public @NotNull @Size(min = 8) String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
    public @NotNull String getMailAddress() {
        return mailAddress;
    }
}
