package de.alex.sw;

import java.util.ArrayList;
import java.util.UUID;

public interface Dao<T> {
    T getByUuid(UUID uuid);

    ArrayList<T> getAll();

    T getPage(int size, int page);

    boolean create(T t);

    boolean update(String uuid, T newT);

    boolean deleteByUuid(String uuid);
}
