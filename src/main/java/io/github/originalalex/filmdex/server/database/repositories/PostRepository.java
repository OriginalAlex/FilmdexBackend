package io.github.originalalex.filmdex.server.database.repositories;

import io.github.originalalex.filmdex.server.database.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

}
