package de.alex.blogster_rest_api.user.service;

import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.http.update_user_info.UpdateUserInfoRequest;
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

    public User createUser(User user) {
        user.setPassword(PwdEncoder.getEncoder().encode(user.getPassword()));
        return userDao.save(user);
    }

    public User updateUser(User user, UpdateUserInfoRequest userInfoRequest) {
        if (userInfoRequest.getFullName() != null) {
            if (userInfoRequest.getFullName().isEmpty()) user.setFullName(null);
            else user.setFullName(userInfoRequest.getFullName());
        }
        if (userInfoRequest.getUsername() != null) user.setUsername(userInfoRequest.getUsername());
        if (userInfoRequest.getMailAddress() != null) user.setMailAddress(userInfoRequest.getMailAddress());
        return userDao.save(user);
    }

    public User updatePassword(User user, String password) {
        user.setPassword(PwdEncoder.getEncoder().encode(password));
        return userDao.save(user);
    }

    public User deleteUser(long id) {
        return userDao.deleteById(id);
    }
}
