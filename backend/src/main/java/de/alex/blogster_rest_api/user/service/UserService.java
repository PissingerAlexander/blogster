package de.alex.blogster_rest_api.user.service;

import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.UserDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(long id) {
        return userDao.findById(id);
    }

    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findUserByMailAddress(String mailAddress) {
        return userDao.findByMailAddress(mailAddress);
    }

    public ArrayList<User> findAllUsers() {
        return userDao.findAll();
    }

    public boolean createUser(User user) {
        user.setPassword(PwdEncoder.passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public void updateUser(long id, User oldUser, User newUser) {
        newUser.setId(id);
        newUser.setRole(oldUser.getRole());
        newUser.setPassword(oldUser.getPassword());
        userDao.save(newUser);
    }
}
