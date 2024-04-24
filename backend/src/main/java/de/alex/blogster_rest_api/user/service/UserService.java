package de.alex.blogster_rest_api.user.service;


import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.UserDao;

import java.util.ArrayList;
import java.util.UUID;

public final class UserService {
    private final UserDao userDao = new UserDao();
    private static final UserService userService = new UserService();

    private UserService() {}

    public static UserService getUserService() {
        return userService;
    }

    public User getUserByUuid(UUID uuid) {
        return userDao.getByUuid(uuid);
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAll();
    }

    public boolean createUser(User user) {
        return userDao.create(user);
    }
}
