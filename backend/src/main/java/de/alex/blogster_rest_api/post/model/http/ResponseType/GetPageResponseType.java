package de.alex.blogster_rest_api.post.model.http.ResponseType;

import de.alex.blogster_rest_api.post.model.Post;

import java.util.List;

public record GetPageResponseType(int page, List<Post> posts) {
}