package de.alex.blogster_rest_api.user.repository;

import de.alex.blogster_rest_api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User deleteById(long id);

    User findByUsernameIgnoreCase(String username);

    User findByMailAddressIgnoreCase(String mailAddress);

    Page<User> findAllBy(Pageable pageable);
}
