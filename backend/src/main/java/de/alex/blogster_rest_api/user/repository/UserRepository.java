package de.alex.blogster_rest_api.user.repository;

import de.alex.blogster_rest_api.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

}
