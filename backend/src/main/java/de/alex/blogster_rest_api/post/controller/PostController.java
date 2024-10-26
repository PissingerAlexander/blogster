package de.alex.blogster_rest_api.post.controller;

import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.comment.model.http.create_comment.CreateCommentResponse;
import de.alex.blogster_rest_api.http.model.response.GetPage;
import de.alex.blogster_rest_api.post.model.Post;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostRequest;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostResponse;
import de.alex.blogster_rest_api.post.model.http.delete_post.DeletePostResponse;
import de.alex.blogster_rest_api.post.model.http.get_page.GetPostPageResponse;
import de.alex.blogster_rest_api.post.model.http.get_post.GetPostResponse;
import de.alex.blogster_rest_api.post.model.http.update_post.UpdatePostRequest;
import de.alex.blogster_rest_api.post.model.http.update_post.UpdatePostResponse;
import de.alex.blogster_rest_api.post.service.PostService;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/post/")
public class PostController {
    private final PostService postService;
    private final BlogService blogService;
    private final UserService userService;


    public PostController(PostService postService, BlogService blogService, UserService userService) {
        this.postService = postService;
        this.blogService = blogService;
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CreatePostRequest createPostRequest, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append('\n')
            );

            return new ResponseEntity<>(new CreatePostResponse(errorMessages.toString()), HttpStatus.CONFLICT);
        }

        if (blogService.findBlogById(createPostRequest.getBlogId()) == null)
            return new ResponseEntity<>(new CreatePostResponse("Blog does not exist"), HttpStatus.NOT_FOUND);
        if (blogService.findBlogById(createPostRequest.getBlogId()).getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new CreatePostResponse("Can't create post on someone else's blog"), HttpStatus.UNAUTHORIZED);
        if (postService.findPostByPostTitleAndBlogId(createPostRequest.getPostTitle(), createPostRequest.getBlogId()) != null)
            return new ResponseEntity<>(new CreatePostResponse("Post with this title already exists on this blog"), HttpStatus.CONFLICT);

        Post post = new Post(
                createPostRequest.getPostTitle(),
                blogService.findBlogById(createPostRequest.getBlogId()),
                createPostRequest.getContent(),
                createPostRequest.getTrackId(),
                createPostRequest.getTrackName()
        );
        return new ResponseEntity<>(new CreatePostResponse(postService.createPost(post)), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable @NotNull long id) {
        if (postService.findPostById(id) == null)
            return new ResponseEntity<>(new GetPostResponse("Post does not exist"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new GetPostResponse(postService.findPostById(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdatePostResponse> updatePost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid UpdatePostRequest updatePostRequest, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append('\n')
            );

            return new ResponseEntity<>(new UpdatePostResponse(errorMessages.toString()), HttpStatus.CONFLICT);
        }

        if (blogService.findBlogById(updatePostRequest.getBlogId()) == null)
            return new ResponseEntity<>(new UpdatePostResponse("Blog does not exist"), HttpStatus.NOT_FOUND);
        if (blogService.findBlogById(updatePostRequest.getBlogId()).getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new UpdatePostResponse("Can't create post on someone else's blog"), HttpStatus.UNAUTHORIZED);

        Post post = postService.updatePost(updatePostRequest);
        return new ResponseEntity<>(new UpdatePostResponse(post), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeletePostResponse> deletePost(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable @NotNull long id) {
        if (userService.findUserById(userPrincipal.getId()).getRole() == Role.ROLE_ADMIN)
            return new ResponseEntity<>(new DeletePostResponse(postService.deletePost(id)), HttpStatus.OK);

        if (postService.findPostById(id) == null)
            return new ResponseEntity<>(new DeletePostResponse("Post does not exist"), HttpStatus.NOT_FOUND);
        if (postService.findPostById(id).getBlog().getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new DeletePostResponse("Can't delete someone else's post"), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(new DeletePostResponse(postService.deletePost(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{blogId}/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Post>> getAllPostsOfBlog(@PathVariable @NotNull long blogId) {
        return new ResponseEntity<>(postService.findPostsByBlogId(blogId), HttpStatus.OK);
    }

    @GetMapping(path = "/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.findAllPosts(), HttpStatus.OK);
    }

    @GetMapping(path = "/{blogId}", produces = "application/json")
    public ResponseEntity<GetPostPageResponse> getPageOfPosts(@PathVariable @NotNull long blogId, @RequestParam int page, @RequestParam int size) {
        if (blogService.findBlogById(blogId) == null)
            return new ResponseEntity<>(new GetPostPageResponse("Blog does not exist"), HttpStatus.NOT_FOUND);

        Page<Post> pages = postService.findPostsPageByBlogId(blogId, page, size);
        long itemCount = pages.getTotalElements();
        int pageCount = pages.getTotalPages();
        List<Post> posts = pages.get().toList();
        return new ResponseEntity<>(new GetPostPageResponse(new GetPage<>(itemCount, pageCount, posts)), HttpStatus.OK);
    }
}
