package de.alex.blogster_rest_api.comment.model;

import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.comment.repository.CommentRepository;
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
    public Comment getPage(int size, int page) {
        // TODO: implement!
        System.out.println("Error getPage in dao not implemented");
        return null;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteById(long id) {
        return commentRepository.deleteById(id);
    }

    public ArrayList<Comment> getCommentsByPostId(long postId) {
        return commentRepository.findByPost_id(postId);
    }

    public void deletePostsByPostId(long postId) {
        commentRepository.deleteByPost_Id(postId);
    }
}
