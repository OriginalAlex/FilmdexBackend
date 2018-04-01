package io.github.originalalex.filmdex.server.database.services;

import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.repositories.UserRepository;
import io.github.originalalex.filmdex.exceptions.EmailsExistsException;
import io.github.originalalex.filmdex.server.dto.UserDto;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean areCredentialsValid(String username, String passwordHash) {
        Iterator<User> users = userRepository.findAll().iterator();
        while (users.hasNext()) {
            User user = users.next();
            if (user.getUsername().equals(username)) { // found the account they're trying to sign into
                return user.getPasswordHash().equals(passwordHash);
            }
        }
        return false; // no point distinguishing between invalid username and invalid password for security reasons
    }

    private boolean emailExists(String email) {
        Iterator<User> users = userRepository.findAll().iterator();
        while (users.hasNext()) {
            if (users.next().getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public User registerNewUser(UserDto details) throws EmailsExistsException{
        String email = details.getEmail();
        if (emailExists(email)) {
            throw new EmailsExistsException("An account with that email already exists");
        }
        String passwordHash = HashUtils.SHA256(details.getPassword());
        User user = new User();
        user.setUsername(details.getUsername());
        user.setPasswordHash(passwordHash);
        user.setEmail(details.getEmail());
        return userRepository.save(user);
    }

}
