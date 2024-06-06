package de.alex.blogster_rest_api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.alex.blogster_rest_api.role.model.Role;
import jakarta.persistence.*;
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
    @NotNull
    @Size(min = 3, max = 64)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Id: " + id + ", Role: " + role + ", Username: " + username + ", Full Name: " + fullName + ", E-Mail: " + mailAddress;
    }
}
