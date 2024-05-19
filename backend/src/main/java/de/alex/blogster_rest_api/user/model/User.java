package de.alex.blogster_rest_api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.alex.blogster_rest_api.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Params:
 *  <ul>
 *      <li>uuid @GeneratedValue</li>
 *      <li>role @NotNull</li>
 *      <li>username @NotNull</li>
 *      <li>password @NotNull</li>
 *      <li>fullName</li>
 *      <li>mailAddress @NotNull</li>
 *  </ul>
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @NotNull
    private Role role = Role.USER;
    @NotNull
    @Size(min = 4, max = 64)
    private String username;
    @NotNull
    @Size(min = 8)
    @JsonIgnore
    private String password;
    private String fullName;
    @NotNull
    private String mailAddress;

    public User() {}

    public User(String username, String password, String fullName, String mailAddress) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.mailAddress = mailAddress;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public @NotNull Role getRole() {
        return role;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
