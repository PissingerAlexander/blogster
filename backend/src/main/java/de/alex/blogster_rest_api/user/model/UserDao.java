package de.alex.blogster_rest_api.user.model;


import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDao implements Dao<User> {
    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public ArrayList<User> findAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User deleteById(long id) {
        return userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public User findByMailAddress(String mailAddress) {
        return userRepository.findByMailAddressIgnoreCase(mailAddress);
    }

    public Page<User> findUserPage(Pageable pageable) {
        return userRepository.findAllBy(pageable);
    }

    public boolean updatePasswordById(long id, String newPassword) {
        return true;
    }
}
