package de.alex.blogster_rest_api.post.repository;

import de.alex.blogster_rest_api.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
    Post findById(long id);
    Post deleteById(long id);

    Post findByPostTitleIgnoreCase(String postTitle);

    Post findByPostTitleIgnoreCaseAndBlog_Id(String id, long blogId);

    ArrayList<Post> findByBlog_Id(long blogId);

    void deleteByBlog_Id(long blogId);

    Page<Post> findAllByBlog_Id(long blogId, Pageable pageable);
}
