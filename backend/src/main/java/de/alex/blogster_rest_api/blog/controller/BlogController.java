package de.alex.blogster_rest_api.blog.controller;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.blog.model.http.create_blog.CreateBlogRequest;
import de.alex.blogster_rest_api.blog.model.http.create_blog.CreateBlogResponse;
import de.alex.blogster_rest_api.blog.model.http.delete_blog.DeleteBlogResponse;
import de.alex.blogster_rest_api.blog.model.http.get_blog.GetBlogResponse;
import de.alex.blogster_rest_api.blog.model.http.get_page.GetBlogPageResponse;
import de.alex.blogster_rest_api.blog.model.http.update_blog.UpdateBlogRequest;
import de.alex.blogster_rest_api.blog.model.http.update_blog.UpdateBlogResponse;
import de.alex.blogster_rest_api.blog.service.BlogService;
import de.alex.blogster_rest_api.http.model.response.GetPage;
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
@RequestMapping(path = "/blog/")
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;

    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CreateBlogResponse> createBlog(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Valid CreateBlogRequest blog, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append('\n')
            );

            return new ResponseEntity<>(new CreateBlogResponse(errorMessages.toString()), HttpStatus.CONFLICT);
        }

        if (blogService.findBlogByBlogNameAndOwnerId(blog.getBlogName(), principal.getId()) != null)
            return new ResponseEntity<>(new CreateBlogResponse("Blog with this name already exists"), HttpStatus.CONFLICT);

        Blog newBlog = new Blog(blog.getBlogName(), userService.findUserById(principal.getId()));
        return new ResponseEntity<>(new CreateBlogResponse(blogService.createBlog(newBlog)), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetBlogResponse> getBlog(@PathVariable @NotNull long id) {
        if (blogService.findBlogById(id) == null)
            return new ResponseEntity<>(new GetBlogResponse("Blog does not exist"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new GetBlogResponse(blogService.findBlogById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Blog>> getAllBlogs(@PathVariable @NotNull long userId) {
        return new ResponseEntity<>(blogService.findBlogsByOwnerId(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateBlogResponse> updateBlog(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid UpdateBlogRequest updateBlogRequest, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error ->
                    errorMessages.append(error.getDefaultMessage()).append('\n')
            );

            return new ResponseEntity<>(new UpdateBlogResponse(errorMessages.toString()), HttpStatus.CONFLICT);
        }
        if (userService.findUserById(userPrincipal.getId()) != blogService.findBlogById(updateBlogRequest.getId()).getOwner())
            return new ResponseEntity<>(new UpdateBlogResponse("Can't change other users blogs"), HttpStatus.UNAUTHORIZED);

        if (blogService.findBlogById(updateBlogRequest.getId()) == null)
            return new ResponseEntity<>(new UpdateBlogResponse("Blog does not exist"), HttpStatus.NOT_FOUND);
        if (blogService.findBlogByBlogNameAndOwnerId(updateBlogRequest.getBlogName(), userPrincipal.getId()) != null)
            return new ResponseEntity<>(new UpdateBlogResponse("This blog name already exists"), HttpStatus.CONFLICT);

        Blog blog = blogService.updateBlog(updateBlogRequest);
        return new ResponseEntity<>(new UpdateBlogResponse(blog), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeleteBlogResponse> deleteBlog(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable @NotNull long id) {
        if (userService.findUserById(userPrincipal.getId()).getRole() == Role.ROLE_ADMIN)
            return new ResponseEntity<>(new DeleteBlogResponse(blogService.deleteBlog(id)), HttpStatus.OK);

        if (blogService.findBlogById(id) == null)
            return new ResponseEntity<>(new DeleteBlogResponse("Blog does not exist"), HttpStatus.NOT_FOUND);
        if (blogService.findBlogById(id).getOwner().getId() != userPrincipal.getId()) {
            return new ResponseEntity<>(new DeleteBlogResponse("Can't delete someone else's blog"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new DeleteBlogResponse(blogService.deleteBlog(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<GetBlogPageResponse> getPageOfBlogs(@PathVariable @NotNull long userId, @RequestParam int page, @RequestParam int size) {
        if (userService.findUserById(userId) == null)
            return new ResponseEntity<>(new GetBlogPageResponse("User does not exist"), HttpStatus.NOT_FOUND);

        Page<Blog> pages = blogService.findBlogsPageByOwnerId(userId, page, size);
        long itemCount = pages.getTotalElements();
        int pageCount = pages.getTotalPages();
        List<Blog> blogs = pages.getContent();
        return new ResponseEntity<>(new GetBlogPageResponse(new GetPage<>(itemCount, pageCount, blogs)), HttpStatus.OK);
    }
}
