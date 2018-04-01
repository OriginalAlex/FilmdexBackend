package io.github.originalalex.filmdex.server.apis;

import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.services.PostService;
import io.github.originalalex.filmdex.server.database.services.UserService;
import io.github.originalalex.filmdex.exceptions.EmailsExistsException;
import io.github.originalalex.filmdex.server.dto.SignIn;
import io.github.originalalex.filmdex.server.dto.UserDto;
import io.github.originalalex.filmdex.utils.api.TokenUtils;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

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
    public @ResponseBody String signIn(@RequestBody @Valid SignIn details, HttpServletResponse response) {
        String username = details.getUsername();
        String password = details.getPassword();
        String passwordHash = HashUtils.SHA256(password);
        User user = userService.areCredentialsValid(username, passwordHash);
        if (user != null) {
            Cookie cookie = new Cookie("Token", TokenUtils.issueUserToken(user.getId(), "USER"));
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) TimeUnit.DAYS.toMillis(1));
            response.addCookie(cookie);
            return "success";
        }
        return "failure";
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    public @ResponseBody String signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("Token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "signed out";
    }

    @RequestMapping(
            value = "/*",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
