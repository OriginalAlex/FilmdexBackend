package io.github.originalalex.filmdex.server.database.repositories;

import io.github.originalalex.filmdex.server.database.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


}
