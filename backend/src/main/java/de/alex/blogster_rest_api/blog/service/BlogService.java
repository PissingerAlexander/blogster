package de.alex.blogster_rest_api.blog.service;

import de.alex.blogster_rest_api.blog.model.Blog;
import de.alex.blogster_rest_api.blog.model.BlogDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {
    private final BlogDao blogDao;

    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public Blog findBlogById(long id) {
        return blogDao.findById(id);
    }

    public Blog findBlogByBlogName(String blogName) {
        return blogDao.findByBlogName(blogName);
    }

    public Blog findBlogByBlogNameAndOwnerId(String blogName, long ownerId) {
        return blogDao.findByBlogNameAndOwnerId(blogName, ownerId);
    }

    public ArrayList<Blog> findAllBlogs() {
        return blogDao.findAll();
    }

    public Blog createBlog(Blog blog) {
        return blogDao.save(blog);
    }

/*
    TODO: Implement
    public Blog updateBlog(Blog blog, Blog newBlog) {
        return blogDao.save(blog);
    }
*/

    public Blog deleteBlog(long id) {
        return blogDao.deleteById(id);
    }

    @Transactional
    public void deleteBlogsByOwnerId(long userId) {
        blogDao.deleteBlogsByOwnerId(userId);
    }
}
