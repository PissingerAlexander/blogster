package de.alex.blogster_rest_api.blog.repository;

import de.alex.blogster_rest_api.blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>, PagingAndSortingRepository<Blog, Long> {
    Blog findById(long id);
    Blog deleteById(long id);

    Blog findByBlogNameIgnoreCase(String blogName);

    Blog findByBlogNameIgnoreCaseAndOwner_Id(String blogName, long ownerId);

    ArrayList<Blog> findByOwner_Id(long ownerId);

    void deleteByOwner_Id(long ownerId);

    Page<Blog> findAllByOwner_Id(long ownerId, Pageable pageable);
}
