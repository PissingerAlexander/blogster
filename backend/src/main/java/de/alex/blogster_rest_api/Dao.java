package de.alex.blogster_rest_api;

import java.util.ArrayList;

public interface Dao<T> {
    T findById(long id);

    ArrayList<T> findAll();

    T save(T t);

    T deleteById(long id);
}
