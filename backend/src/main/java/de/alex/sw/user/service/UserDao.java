package de.alex.sw.user.service;

import de.alex.sw.Dao;
import de.alex.sw.user.model.UserModel;

import java.util.ArrayList;
import java.util.Optional;

public class UserDao implements Dao<UserModel> {

    @Override
    public Optional<UserModel> get(String uuid) {
        return Optional.empty();
    }

    @Override
    public ArrayList<UserModel> getAll() {
        return null;
    }

    @Override
    public void create(UserModel userModel) {

    }

    @Override
    public void update(UserModel userModel, UserModel newT) {

    }

    @Override
    public void delete(long id) {

    }
}
