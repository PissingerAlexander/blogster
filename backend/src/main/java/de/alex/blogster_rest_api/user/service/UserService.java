package de.alex.blogster_rest_api.user.service;


import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public final class UserService implements UserServiceInterface {
    @Autowired
    private UserDao userDao;

    public UserService() {}

    public User getUserByUuid(UUID uuid) {
        return userDao.getByUuid(uuid);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAll();
    }

    public boolean createUser(User user) {
        return userDao.create(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }
}
