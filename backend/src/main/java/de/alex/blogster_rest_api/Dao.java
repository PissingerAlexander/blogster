package de.alex.blogster_rest_api;

import java.util.ArrayList;

public interface Dao<T> {
    T findById(long id);

    ArrayList<T> findAll();

    T getPage(int size, int page);

    boolean save(T t);

    boolean updateById(long id, T newT);

    boolean deleteById(long id);
}
