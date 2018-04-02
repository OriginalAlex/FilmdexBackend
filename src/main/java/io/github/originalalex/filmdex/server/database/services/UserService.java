package io.github.originalalex.filmdex.server.database.services;

import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.repositories.UserRepository;
import io.github.originalalex.filmdex.exceptions.AlreadyExistsException;
import io.github.originalalex.filmdex.server.dto.UserDto;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User areCredentialsValid(String username, String passwordHash) {
        Iterator<User> users = userRepository.findAll().iterator();
        while (users.hasNext()) {
            User user = users.next();
            if (user.getUsername().equals(username)) { // found the account they're trying to sign into
                return (user.getPasswordHash().equals(passwordHash)) ? user : null;
            }
        }
        return null; // no point distinguishing between invalid username and invalid password for security reasons
    }

    public User fetchById(long id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private boolean emailOrUserExists(String username, String email) {
        Iterator<User> users = userRepository.findAll().iterator();
        while (users.hasNext()) {
            User next = users.next();
            if (next.getEmail().equals(email) || next.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public User registerNewUser(UserDto details) throws AlreadyExistsException {
        String email = details.getEmail();
        if (emailOrUserExists(details.getUsername(), email)) {
            throw new AlreadyExistsException("An account with that email already exists");
        }
        String passwordHash = HashUtils.SHA256(details.getPassword());
        User user = new User();
        user.setUsername(details.getUsername());
        user.setPasswordHash(passwordHash);
        user.setEmail(details.getEmail());
        return userRepository.save(user);
    }

}
