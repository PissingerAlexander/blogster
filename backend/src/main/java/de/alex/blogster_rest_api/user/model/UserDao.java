package de.alex.blogster_rest_api.user.model;


import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.user.repository.UserRepository;
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

    public User getPage(int size, int page) {
        //TODO: implement!
        return null;
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

    public boolean updatePasswordById(long id, String newPassword) {
        return true;
    }
}
