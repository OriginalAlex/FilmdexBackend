package io.github.originalalex.filmdex.server.apis;

import io.github.originalalex.filmdex.server.database.services.PostService;
import io.github.originalalex.filmdex.server.database.services.UserService;
import io.github.originalalex.filmdex.exceptions.EmailsExistsException;
import io.github.originalalex.filmdex.server.dto.SignIn;
import io.github.originalalex.filmdex.server.dto.UserDto;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class AccountsAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(@RequestBody @Valid UserDto user) {
        try {
            userService.registerNewUser(user);
        } catch(EmailsExistsException e) {
            return "Email already exists";
        }
        return "Registered user!";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public @ResponseBody String signIn(@RequestBody @Valid SignIn details) {
        String username = details.getUsername();
        String password = details.getPassword();
        String passwordHash = HashUtils.SHA256(password);
        if (userService.areCredentialsValid(username, passwordHash)) {
            return "correct!";
        }
        return "wrong!";
    }

    @RequestMapping(
            value = "/*",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
