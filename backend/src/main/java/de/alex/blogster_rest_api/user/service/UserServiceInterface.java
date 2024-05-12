package de.alex.blogster_rest_api.user.service;

import de.alex.blogster_rest_api.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {
    public User findUserByUsername(String username);// throws UsernameNotFoundException;
}
