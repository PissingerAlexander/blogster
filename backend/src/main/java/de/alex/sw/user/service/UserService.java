package de.alex.sw.user.service;

import de.alex.sw.user.model.UserModel;

import java.util.ArrayList;

public final class UserService {
    private final UserDao userDao = new UserDao();
    private static final UserService userService = new UserService();

    private UserService() {}

    public static UserService getUserService() {
        return userService;
    }

    private ArrayList<UserModel> getAllUsers() {
        return userDao.getAll();
    }
}
