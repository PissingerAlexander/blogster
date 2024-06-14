package de.alex.blogster_rest_api.post.service;

import de.alex.blogster_rest_api.post.model.Post;
import de.alex.blogster_rest_api.post.model.PostDao;
import de.alex.blogster_rest_api.post.model.http.update_post.UpdatePostRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    private final PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public Post findPostById(Long id) {
        return postDao.findById(id);
    }

    public Post findPostByPostTitle(String postTitle) {
        return postDao.findByTitle(postTitle);
    }

    public ArrayList<Post> findBlogByBlogId(Long blogId) {
        return postDao.findByBlogId(blogId);
    }

    public Post findPostByPostTitleAndBlogId(String postTitle, Long blogId) {
        return postDao.findByPostTitleAndBlogId(postTitle, blogId);
    }

    public ArrayList<Post> findAllPosts() {
        return postDao.findAll();
    }

    public Post createPost(Post post) {
        return postDao.save(post);
    }

    public Post updatePost(UpdatePostRequest updatePostRequest) {
        Post post = this.findPostById(updatePostRequest.getId());
        post.setPostTitle(updatePostRequest.getPostTitle());
        post.setContent(updatePostRequest.getContent());
        return post;
    }

    public Post deletePost(long id) {
        return postDao.deleteById(id);
    }

    @Transactional
    public void deletePostsByBlogId(Long blogId) {
        postDao.deletePostsByBlogId(blogId);
    }
}
