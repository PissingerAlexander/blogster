package de.alex.blogster_rest_api.post.model;

import de.alex.blogster_rest_api.Dao;
import de.alex.blogster_rest_api.post.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PostDao implements Dao<Post> {
    private final PostRepository postRepository;

    public PostDao(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public ArrayList<Post> findAll() {
        return (ArrayList<Post>) postRepository.findAll();
    }

    @Override
    public Post getPage(int size, int page) {
        //TODO: implement!
        System.out.println("Error getPage in dao not implemented");
        return null;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post deleteById(long id) {
        return postRepository.deleteById(id);
    }

    public Post findByTitle(String title) {
        return postRepository.findByPostTitleIgnoreCase(title);
    }

    public ArrayList<Post> findByBlogId(long id) {
        return postRepository.findByBlog_Id(id);
    }

    public Post findByPostTitleAndBlogId(String postTitle, long blogId) {
        return postRepository.findByPostTitleIgnoreCaseAndBlog_Id(postTitle, blogId);
    }

    public void deletePostsByBlogId(long blogId) {
        postRepository.deleteByBlog_Id(blogId);
    }
}
