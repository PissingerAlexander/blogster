package de.alex.blogster_rest_api;

import java.util.ArrayList;
import java.util.UUID;

public interface Dao<T> {
    T getByUuid(UUID uuid);

    ArrayList<T> getAll();

    T getPage(int size, int page);

    boolean create(T t);

    boolean updateByUuid(UUID uuid, T newT);

    boolean deleteByUuid(UUID uuid);
}
