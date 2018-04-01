package io.github.originalalex.filmdex.server.apis;

import io.github.originalalex.filmdex.server.database.services.UserService;
import io.github.originalalex.filmdex.exceptions.EmailsExistsException;
import io.github.originalalex.filmdex.server.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
public class AccountsAPI {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/accounts/create", method = RequestMethod.POST)
    public @ResponseBody String create(@RequestBody @Valid UserDto user) {
        System.out.println(user.getPassword());
        System.out.println(user.getEmail() + " and "+  user.getUsername());
        try {
            userService.registerNewUser(user);
        } catch(EmailsExistsException e) {
            return "Email already exists";
        }
        return "Registered user!";
    }

    @RequestMapping(
            value = "/*",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        System.out.println("hello!");
        return new ResponseEntity(HttpStatus.OK);
    }

}
