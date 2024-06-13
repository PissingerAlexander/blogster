package de.alex.blogster_rest_api.post.controller;

import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.post.model.Post;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostRequest;
import de.alex.blogster_rest_api.post.model.http.create_post.CreatePostResponse;
import de.alex.blogster_rest_api.post.model.http.get_post.GetPostResponse;
import de.alex.blogster_rest_api.post.service.PostService;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreatePostRequest createPostRequest) {
        if (blogService.findBlogById(createPostRequest.getBlogId()).getOwner().getId() != userPrincipal.getId())
            return new ResponseEntity<>(new CreatePostResponse("Can't create post on someone else's blog"), HttpStatus.UNAUTHORIZED);

        if (postService.findPostByPostTitleAndBlogId(createPostRequest.getPostTitle(), createPostRequest.getBlogId()) != null)
            return new ResponseEntity<>(new CreatePostResponse("Post with this title already exists on this blog"), HttpStatus.CONFLICT);

        Post post = new Post(
                createPostRequest.getPostTitle(),
                blogService.findBlogById(createPostRequest.getBlogId()),
                userService.findUserById(userPrincipal.getId()),
                createPostRequest.getContent()
        );
        return new ResponseEntity<>(new CreatePostResponse(postService.createPost(post)), HttpStatus.OK);
    }

    //TODO: check if post exists
    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable long id) {
        return new ResponseEntity<>(new GetPostResponse(postService.findPostById(id)), HttpStatus.OK);
    }
}
