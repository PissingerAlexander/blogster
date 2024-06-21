package de.alex.blogster_rest_api.security.config;

import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DefaultAdminConfiguration {
    private final UserService userService;


    public DefaultAdminConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public User addAdminUser() {
        if (userService.findUserByUsername("admin") == null) {
            User admin = new User(
                    "admin",
                    "password",
                    null,
                    "admin@mail.com"
            );
            admin.setRole(Role.ROLE_ADMIN);
            userService.createUser(admin);
        }

        return userService.findUserByUsername("admin");
    }
}
