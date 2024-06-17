package de.alex.blogster_rest_api.comment.model;

import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.comment.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CommentDao implements Dao<Comment> {
    private final CommentRepository commentRepository;

    public CommentDao(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public ArrayList<Comment> findAll() {
        return (ArrayList<Comment>) commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteById(long id) {
        return commentRepository.deleteById(id);
    }

    public ArrayList<Comment> findCommentsByPostId(long postId) {
        return commentRepository.findByPost_id(postId);
    }

    public void deleteCommentsByPostId(long postId) {
        commentRepository.deleteByPost_Id(postId);
    }

    public Page<Comment> findCommentPageByPostId(long postId, Pageable pageable) {
        return commentRepository.findAllByPost_Id(postId, pageable);
    }
}
