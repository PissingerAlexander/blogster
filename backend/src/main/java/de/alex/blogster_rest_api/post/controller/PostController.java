package de.alex.blogster_rest_api.post.controller;

import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.post.model.Post;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostRequest;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostResponse;
import de.alex.blogster_rest_api.post.model.http.delete_post.DeletePostResponse;
import de.alex.blogster_rest_api.post.model.http.get_post.GetPostResponse;
import de.alex.blogster_rest_api.post.model.http.update_post.UpdatePostRequest;
import de.alex.blogster_rest_api.post.model.http.update_post.UpdatePostResponse;
import de.alex.blogster_rest_api.post.service.PostService;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/post/")
public class PostController {
    private final PostService postService;
    private final BlogService blogService;


    public PostController(PostService postService, BlogService blogService) {
        this.postService = postService;
        this.blogService = blogService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreatePostRequest createPostRequest) {
        if (blogService.findBlogById(createPostRequest.getBlogId()) == null)
            return new ResponseEntity<>(new CreatePostResponse("Blog does not exist"), HttpStatus.CONFLICT);
        if (blogService.findBlogById(createPostRequest.getBlogId()).getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new CreatePostResponse("Can't create post on someone else's blog"), HttpStatus.UNAUTHORIZED);
        if (postService.findPostByPostTitleAndBlogId(createPostRequest.getPostTitle(), createPostRequest.getBlogId()) != null)
            return new ResponseEntity<>(new CreatePostResponse("Post with this title already exists on this blog"), HttpStatus.CONFLICT);

        Post post = new Post(
                createPostRequest.getPostTitle(),
                blogService.findBlogById(createPostRequest.getBlogId()),
                createPostRequest.getContent()
        );
        return new ResponseEntity<>(new CreatePostResponse(postService.createPost(post)), HttpStatus.OK);
    }

    //TODO: possibly check if post exists
    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable long id) {
        return new ResponseEntity<>(new GetPostResponse(postService.findPostById(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdatePostResponse> updatePost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdatePostRequest updatePostRequest) {
        if (blogService.findBlogById(updatePostRequest.getId()) == null)
            return new ResponseEntity<>(new UpdatePostResponse("Blog does not exist"), HttpStatus.CONFLICT);
        if (blogService.findBlogById(updatePostRequest.getBlogId()).getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new UpdatePostResponse("Can't create post on someone else's blog"), HttpStatus.UNAUTHORIZED);

        Post post = postService.updatePost(updatePostRequest);
        return new ResponseEntity<>(new UpdatePostResponse(post), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeletePostResponse> deletePost(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long id) {
        if (postService.findPostById(id).getBlog().getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new DeletePostResponse("Can't delete someone else's post"), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(new DeletePostResponse(postService.deletePost(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{blogId}/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Post>> getAllPosts(@PathVariable long blogId) {
        return new ResponseEntity<>(postService.findPostsByBlogId(blogId), HttpStatus.OK);
    }
}
