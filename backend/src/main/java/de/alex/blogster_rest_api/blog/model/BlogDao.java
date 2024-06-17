package de.alex.blogster_rest_api.blog.model;

import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.blog.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BlogDao implements Dao<Blog> {
    private final BlogRepository blogRepository;

    public BlogDao(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    @Override
    public Blog findById(long id) {
        return blogRepository.findById(id);
    }

    @Override
    public ArrayList<Blog> findAll() {
        return (ArrayList<Blog>) blogRepository.findAll();
    }

    public ArrayList<Blog> getPage(long id, int size, int page) {
        //TODO: implement!
        return null;
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog deleteById(long id) {
        return blogRepository.deleteById(id);
    }

    public Blog findByBlogName(String blogName) {
        return blogRepository.findByBlogNameIgnoreCase(blogName);
    }

    public ArrayList<Blog> findByOwnerId(long ownerId) {
        return blogRepository.findByOwner_Id(ownerId);
    }

    public Page<Blog> findBlogPageByOwnerId(long ownerId, Pageable pageable) {
        return blogRepository.findAllByOwner_Id(ownerId, pageable);
    }

    public Blog findByBlogNameAndOwnerId(String blogName, long ownerId) {
        return blogRepository.findByBlogNameIgnoreCaseAndOwner_Id(blogName, ownerId);
    }

    public void deleteBlogsByOwnerId(long ownerId) {
        blogRepository.deleteByOwner_Id(ownerId);
    }
}
