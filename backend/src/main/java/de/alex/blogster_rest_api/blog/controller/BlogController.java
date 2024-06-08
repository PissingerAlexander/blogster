package de.alex.blogster_rest_api.blog.controller;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.blog.model.http.create_blog.CreateBlogRequest;
import de.alex.blogster_rest_api.blog.model.http.create_blog.CreateBlogResponse;
import de.alex.blogster_rest_api.blog.model.http.delete_blog.DeleteBlogResponse;
import de.alex.blogster_rest_api.blog.model.http.get_blog.GetBlogResponse;
import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/blog/")
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;

    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreateBlogResponse> createBlog(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Validated CreateBlogRequest blog) {
        if (blogService.findBlogByBlogNameAndOwnerId(blog.getBlogName(), principal.getId()) != null)
            return new ResponseEntity<>(new CreateBlogResponse("Blog with this name already exists"), HttpStatus.CONFLICT);

        Blog newBlog = new Blog(blog.getBlogName(), userService.findUserById(principal.getId()));
        return new ResponseEntity<>(new CreateBlogResponse(blogService.createBlog(newBlog)), HttpStatus.OK);
    }

    //TODO: check if requesting user is owner? or can anyone see your blogs?
    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetBlogResponse> getBlog(@PathVariable long id) {
        return new ResponseEntity<>(new GetBlogResponse(blogService.findBlogById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Blog>> getAllBlogs(@PathVariable long userId) {
        return new ResponseEntity<>(blogService.findBlogByOwnerId(userId), HttpStatus.OK);
    }

/*
    TODO: implement blogService.updateBlog first
    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public Blog updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }
*/

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeleteBlogResponse> deleteBlog(@AuthenticationPrincipal UserPrincipal principal, @PathVariable long id) {
        System.out.println(blogService.findBlogById(id).getOwner().getId() + " " + principal.getId() +  " " + (blogService.findBlogById(id).getOwner().getId() != principal.getId()));
        if (blogService.findBlogById(id).getOwner().getId() != principal.getId()) {
            return new ResponseEntity<>(new DeleteBlogResponse("Can't delete someone else's blog"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new DeleteBlogResponse(blogService.deleteBlog(id)), HttpStatus.OK);
    }
}
