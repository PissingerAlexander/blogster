package de.alex.blogster_rest_api.comment.service;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.comment.model.CommentDao;
import de.alex.blogster_rest_api.comment.model.http.update_comment.UpdateCommentRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {
    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public Comment findCommentById(long id) {
        return commentDao.findById(id);
    }

    public ArrayList<Comment> findCommentsByPostId(long postId) {
        return commentDao.findCommentsByPostId(postId);
    }

    public ArrayList<Comment> findAllComments() {
        return commentDao.findAll();
    }

    public Comment createComment(Comment comment) {
        return commentDao.save(comment);
    }

    public Comment updateComment(UpdateCommentRequest updateCommentRequest) {
        Comment comment = findCommentById(updateCommentRequest.getId());
        comment.setComment(updateCommentRequest.getComment());
        return commentDao.save(comment);
    }

    public Comment deleteComment(long id) {
        return commentDao.deleteById(id);
    }

    public void deleteCommentsByPostId(long postId) {
        commentDao.deleteCommentsByPostId(postId);
    }
}
