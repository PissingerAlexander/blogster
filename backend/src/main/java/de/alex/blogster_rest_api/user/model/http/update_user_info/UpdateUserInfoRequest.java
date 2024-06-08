package de.alex.blogster_rest_api.user.model.http.update_user_info;

public class UpdateUserInfoRequest {
    private String fullName;
    private String username;
    private String mailAddress;

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getMailAddress() {
        return mailAddress;
    }
}
