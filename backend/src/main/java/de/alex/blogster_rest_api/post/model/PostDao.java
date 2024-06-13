package de.alex.blogster_rest_api.post.model;

import de.alex.blogster_rest_api.Dao;

import java.util.ArrayList;

public class PostDao implements Dao<Post> {
    @Override
    public Post findById(long id) {
        return null;
    }

    @Override
    public ArrayList<Post> findAll() {
        return null;
    }

    @Override
    public Post getPage(int size, int page) {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post deleteById(long id) {
        return null;
    }
}
