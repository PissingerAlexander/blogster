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
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<CreateBlogResponse> createBlog(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Validated CreateBlogRequest blog) {
        if (blogService.findBlogByBlogNameAndOwnerId(blog.getBlogName(), principal.getId()) != null)
            return new ResponseEntity<>(new CreateBlogResponse("Blog with this name already exists"), HttpStatus.CONFLICT);

        Blog newBlog = new Blog(blog.getBlogName(), userService.findUserById(principal.getId()));
        return new ResponseEntity<>(new CreateBlogResponse(blogService.createBlog(newBlog)), HttpStatus.OK);
    }

    //TODO: check if requesting user is owner? or can anyone see your blogs? // check if blog exists
    @GetMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<GetBlogResponse> getBlog(@PathVariable long id) {
        return new ResponseEntity<>(new GetBlogResponse(blogService.findBlogById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/all/", produces = "application/json")
    public ResponseEntity<ArrayList<Blog>> getAllBlogs(@PathVariable long userId) {
        return new ResponseEntity<>(blogService.findBlogsByOwnerId(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateBlogResponse> updateBlog(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateBlogRequest updateBlogRequest) {
        if (userService.findUserById(userPrincipal.getId()) != blogService.findBlogById(updateBlogRequest.getId()).getOwner())
            return new ResponseEntity<>(new UpdateBlogResponse("Can't change other users blogs"), HttpStatus.UNAUTHORIZED);

        if (blogService.findBlogByBlogNameAndOwnerId(updateBlogRequest.getBlogName(), userPrincipal.getId()) != null)
            return new ResponseEntity<>(new UpdateBlogResponse("This blog name already exists"), HttpStatus.CONFLICT);

        Blog blog = blogService.updateBlog(updateBlogRequest);
        return new ResponseEntity<>(new UpdateBlogResponse(blog), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}/", produces = "application/json")
    public ResponseEntity<DeleteBlogResponse> deleteBlog(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long id) {
        if (userService.findUserById(userPrincipal.getId()).getRole() == Role.ROLE_ADMIN)
            return new ResponseEntity<>(new DeleteBlogResponse(blogService.deleteBlog(id)), HttpStatus.OK);

        if (blogService.findBlogById(id).getOwner().getId() != userPrincipal.getId()) {
            return new ResponseEntity<>(new DeleteBlogResponse("Can't delete someone else's blog"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new DeleteBlogResponse(blogService.deleteBlog(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/page", produces = "application/json")
    public ResponseEntity<GetBlogPageResponse> getPageOfBlogs(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam @NotNull int page, @RequestParam @NotNull int size) {
        Page<Blog> pages = blogService.findBlogsPageByOwnerId(userPrincipal.getId(), page, size);
        long itemCount = pages.getTotalElements();
        int pageCount = pages.getTotalPages();
        List<Blog> blogs = pages.getContent();
        return new ResponseEntity<>(new GetBlogPageResponse(new GetPage<>(itemCount, pageCount, blogs)), HttpStatus.OK);
    }
}
