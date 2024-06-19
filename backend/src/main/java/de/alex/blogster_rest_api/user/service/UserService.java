package de.alex.blogster_rest_api.user.service;

import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.user.model.http.update_user_info.UpdateUserInfoRequest;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.model.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserDao userDao;
    private final BlogService blogService;

    public UserService(UserDao userDao, BlogService blogService) {
        this.userDao = userDao;
        this.blogService = blogService;
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

    public Page<User> findUsersPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDao.findUserPage(pageable);
    }

    public User createUser(User user) {
        user.setPassword(PwdEncoder.getEncoder().encode(user.getPassword()));
        return userDao.save(user);
    }

    public User updateUser(User user, UpdateUserInfoRequest userInfoRequest) {
        if (userInfoRequest.getFullName().isEmpty()) user.setFullName(null);
        else user.setFullName(userInfoRequest.getFullName());
        user.setUsername(userInfoRequest.getUsername());
        user.setMailAddress(userInfoRequest.getMailAddress());
        return userDao.save(user);
    }

    public User updatePassword(User user, String password) {
        user.setPassword(PwdEncoder.getEncoder().encode(password));
        return userDao.save(user);
    }

    public void setSpotifyAuthorization(User user, Boolean value) {
        user.setSpotifyAuthorized(value);
        userDao.save(user);
    }

    @Transactional
    public User deleteUser(long id) {
        blogService.deleteBlogsByOwnerId(id);
        return userDao.deleteById(id);
    }
}
