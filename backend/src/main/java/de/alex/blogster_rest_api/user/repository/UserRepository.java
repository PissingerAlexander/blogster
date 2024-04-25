package de.alex.blogster_rest_api.user.repository;

import de.alex.blogster_rest_api.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsernameIgnoreCase(String username);
}
