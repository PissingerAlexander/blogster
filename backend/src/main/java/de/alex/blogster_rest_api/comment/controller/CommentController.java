package de.alex.blogster_rest_api.comment.controller;

import de.alex.blogster_rest_api.comment.model.Comment;
import de.alex.blogster_rest_api.comment.model.http.create_comment.CreateCommentRequest;
import de.alex.blogster_rest_api.comment.model.http.create_comment.CreateCommentResponse;
import de.alex.blogster_rest_api.comment.model.http.delete_comment.DeleteCommentResponse;
import de.alex.blogster_rest_api.comment.model.http.get_comment.GetCommentResponse;
import de.alex.blogster_rest_api.comment.model.http.update_comment.UpdateCommentRequest;
import de.alex.blogster_rest_api.comment.model.http.update_comment.UpdateCommentResponse;
import de.alex.blogster_rest_api.comment.service.CommentService;
import de.alex.blogster_rest_api.post.service.PostService;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/comment/")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreateCommentResponse> createComment(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreateCommentRequest createCommentRequest) {
        if (postService.findPostById(createCommentRequest.getPostId()) == null)
            return new ResponseEntity<>(new CreateCommentResponse("Post does not exist"), HttpStatus.CONFLICT);

        Comment comment = new Comment(
                createCommentRequest.getComment(),
                userService.findUserById(userPrincipal.getId()),
                postService.findPostById(createCommentRequest.getPostId())
        );
        return new ResponseEntity<>(new CreateCommentResponse(commentService.createComment(comment)), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long id) {
        return new ResponseEntity<>(new GetCommentResponse(commentService.findCommentById(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateCommentResponse> updateComment(@RequestBody UpdateCommentRequest updateCommentRequest) {
        if (postService.findPostById(updateCommentRequest.getPostId()) == null)
            return new ResponseEntity<>(new UpdateCommentResponse("Post does not exist"), HttpStatus.CONFLICT);

        Comment comment = commentService.updateComment(updateCommentRequest);
        return new ResponseEntity<>(new UpdateCommentResponse(comment), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeleteCommentResponse> deleteComment(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
        if (commentService.findCommentById(id).getAuthor().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new DeleteCommentResponse("Can't delete someone else's comment"), HttpStatus.CONFLICT);
        return new ResponseEntity<>(new DeleteCommentResponse(commentService.deleteComment(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{postId}/all", produces = "application/json")
    public ResponseEntity<ArrayList<Comment>> getAllComments(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.findCommentsByPostId(postId), HttpStatus.OK);
    }
}
