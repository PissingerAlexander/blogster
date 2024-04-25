package de.alex.blogster_rest_api.user.model;


import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserDao implements Dao<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByUuid(UUID uuid) {
        return null;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User getPage(int size, int page) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public boolean create(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateByUuid(UUID uuid, User newT) {
        return true;
    }

    @Override
    public boolean deleteByUuid(UUID uuid) {
        return true;
    }
}
