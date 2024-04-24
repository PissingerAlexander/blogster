package de.alex.blogster_rest_api.user.model;


import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.user.repository.UserRepository;
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

    public User getUserByName(String name) {
        return null;
    }

    @Override
    public User getPage(int size, int page) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public boolean create(User userModel) {
        return true;
    }

    @Override
    public boolean updateByUuid(String uuid, User newT) {
        return true;
    }

    @Override
    public boolean deleteByUuid(String uuid) {
        return true;
    }
}
