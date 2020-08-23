package io.github.originalalex.filmdex.server.database.services;

import io.github.originalalex.filmdex.server.database.models.Post;
import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPostsByThread(String thread) {
        Iterator<Post> posts = postRepository.findAll().iterator();
        List<Post> result = new ArrayList<>();
        while (posts.hasNext()) {
            Post post = posts.next();
            if (post.getThread().equals(thread)) {
                result.add(post);
            }
        }
        return result;
    }

    public Post fetchById(long id) {
        return postRepository.findById(id).get();
    }

    public Post createPost(User poster, String thread, long replyingToId, String body) {
        Post post = new Post();
        post.setPoster(poster);
        post.setThread(thread);
        post.setReplyingTo(replyingToId);
        post.setBody(body);
        post.setTimePosted(new Date(System.currentTimeMillis()));
        return postRepository.save(post);
    }

}
