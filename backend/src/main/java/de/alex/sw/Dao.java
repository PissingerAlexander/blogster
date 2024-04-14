package de.alex.sw;

import java.util.ArrayList;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(String uuid);

    ArrayList<T> getAll();

    void create(T t);

    void update(String uuid, T newT);

    void delete(String uuid);
}
