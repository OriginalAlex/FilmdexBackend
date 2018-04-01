package io.github.originalalex.filmdex.server.database.services;

import io.github.originalalex.filmdex.server.database.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;



}
