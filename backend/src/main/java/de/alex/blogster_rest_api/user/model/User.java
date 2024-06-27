package de.alex.blogster_rest_api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.alex.blogster_rest_api.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Params:
 *  <ul>
 *      <li>id @GeneratedValue</li>
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private Role role = Role.ROLE_USER;
    @NotBlank
    @Size(min = 3, max = 64)
    private String username;
    @NotBlank
    @Size(min = 8)
    @JsonIgnore
    private String password;
    private String fullName;
    @Email
    private String mailAddress;
    @NotNull
    private Boolean spotifyAuthorized = false;

    public User() {}

    public User(@NotBlank @Size(min = 3, max = 64) String username,@NotBlank @Size(min = 8) String password, String fullName,@Email String mailAddress) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.mailAddress = mailAddress;
    }

    public long getId() {
        return id;
    }

    public @NotNull Role getRole() {
        return role;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public @NotBlank @Size(min = 3, max = 64) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 64) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 8) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8) String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public @NotNull String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(@NotNull String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public @NotNull Boolean getSpotifyAuthorized() {
        return spotifyAuthorized;
    }

    public void setSpotifyAuthorized(@NotNull Boolean spotify) {
        this.spotifyAuthorized = spotify;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Role: " + role + ", Username: " + username + ", Full Name: " + fullName + ", E-Mail: " + mailAddress;
    }
}
