package de.alex.blogster_rest_api;

import java.util.ArrayList;
import java.util.UUID;

public interface Dao<T> {
    T findByUuid(UUID uuid);

    ArrayList<T> findAll();

    T getPage(int size, int page);

    boolean save(T t);

    boolean updateByUuid(UUID uuid, T newT);

    boolean deleteByUuid(UUID uuid);
}
