package de.alex.blogster_rest_api.blog.repository;

import de.alex.blogster_rest_api.blog.model.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {
    Blog findById(long id);
    Blog deleteById(long id);

    Blog findByBlogNameIgnoreCase(String blogName);

    Blog findByOwner_Id(long ownerId);
}
