package de.alex.blogster_rest_api.blog.model;

import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.blog.repository.BlogRepository;
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

    @Override
    public Blog getPage(int size, int page) {
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
}
