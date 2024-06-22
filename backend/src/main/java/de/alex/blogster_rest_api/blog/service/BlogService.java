package de.alex.blogster_rest_api.blog.service;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.blog.model.BlogDao;
import de.alex.blogster_rest_api.blog.model.http.update_blog.UpdateBlogRequest;
import de.alex.blogster_rest_api.post.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {
    private final BlogDao blogDao;
    private final PostService postService;

    public BlogService(BlogDao blogDao, PostService postService) {
        this.blogDao = blogDao;
        this.postService = postService;
    }

    public Blog findBlogById(long id) {
        return blogDao.findById(id);
    }

    public Blog findBlogByBlogName(String blogName) {
        return blogDao.findByBlogName(blogName);
    }

    public ArrayList<Blog> findBlogsByOwnerId(long ownerId) {
        return blogDao.findByOwnerId(ownerId);
    }

    public Blog findBlogByBlogNameAndOwnerId(String blogName, long ownerId) {
        return blogDao.findByBlogNameAndOwnerId(blogName, ownerId);
    }

    public ArrayList<Blog> findAllBlogs() {
        return blogDao.findAll();
    }

    public Page<Blog> findBlogsPageByOwnerId(long ownerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogDao.findBlogPageByOwnerId(ownerId, pageable);
    }

    public Blog createBlog(Blog blog) {
        return blogDao.save(blog);
    }

    public Blog updateBlog(UpdateBlogRequest updateBlogRequest) {
        Blog blog = findBlogById(updateBlogRequest.getId());
        blog.setBlogName(updateBlogRequest.getBlogName());
        return blogDao.save(blog);
    }

    @Transactional
    public Blog deleteBlog(long id) {
        postService.deletePostsByBlogId(id);
        return blogDao.deleteById(id);
    }

    @Transactional
    public void deleteBlogsByOwnerId(long userId) {
        ArrayList<Blog> toDelete = findBlogsByOwnerId(userId);
        toDelete.forEach(blog -> postService.deletePostsByBlogId(blog.getId()));
        blogDao.deleteBlogsByOwnerId(userId);
    }
}
