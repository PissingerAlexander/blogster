package de.alex.blogster_rest_api.blog.repository;

import de.alex.blogster_rest_api.blog.model.Blog;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {
    Blog findById(long id);
    Blog deleteById(long id);

    Blog findByBlogNameIgnoreCase(String blogName);

    Blog findByBlogNameIgnoreCaseAndOwner_Id(String blogName, long ownerId);

    Blog findByOwner_Id(long ownerId);

    void deleteByOwner_Id(long ownerId);
}
