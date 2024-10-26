package de.alex.blogster_rest_api.comment.repository;

import de.alex.blogster_rest_api.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {
    Comment findById(long id);
    Comment deleteById(long id);

    ArrayList<Comment> findByPost_id(long postId);

    void deleteByPost_Id(long postId);

    Page<Comment> findAllByPost_Id(long postId, Pageable pageable);
}
