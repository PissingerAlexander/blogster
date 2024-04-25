package de.alex.blogster_rest_api.user.service;

import de.alex.blogster_rest_api.user.model.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface {
    public User findUserByUsername(String username);// throws UsernameNotFoundException;
}
