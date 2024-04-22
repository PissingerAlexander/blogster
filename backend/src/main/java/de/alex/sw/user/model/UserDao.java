package de.alex.sw.user.model;

import de.alex.sw.Dao;

import java.util.ArrayList;
import java.util.UUID;

public class UserDao implements Dao<User> {

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
    public boolean update(String uuid, User newT) {
        return true;
    }

    @Override
    public boolean deleteByUuid(String uuid) {
        return true;
    }
}
