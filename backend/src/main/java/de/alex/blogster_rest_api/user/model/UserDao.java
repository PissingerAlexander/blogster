package de.alex.blogster_rest_api.user.model;


import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserDao implements Dao<User> {
    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public User findByMailAddress(String mailAddress) {
        return userRepository.findByMailAddressIgnoreCase(mailAddress);
    }

    public boolean updatePasswordByUuid(UUID uuid, String newPassword) {
        return true;
    }

    @Override
    public User getPage(int size, int page) {
        return null;
    }

    @Override
    public ArrayList<User> findAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateByUuid(UUID uuid, User newT) {
        newT.setUuid(uuid);
        userRepository.save(newT);
        return true;
    }

    @Override
    public boolean deleteByUuid(UUID uuid) {
        userRepository.deleteByUuid(uuid);
        return true;
    }
}
