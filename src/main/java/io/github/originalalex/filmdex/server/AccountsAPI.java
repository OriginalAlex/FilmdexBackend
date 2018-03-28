package io.github.originalalex.filmdex.server;

import com.google.gson.JsonObject;
import io.github.originalalex.filmdex.database.DatabaseFetcher;
import io.github.originalalex.filmdex.database.DatabaseModifier;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import io.github.originalalex.filmdex.utils.io.JSONUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
public class AccountsAPI {

    private static DatabaseFetcher dbFetcher;
    private static DatabaseModifier dbModifier;

    public static void setDbFetcher(DatabaseFetcher fetcher) {
        dbFetcher = fetcher;
    }

    public void setDbModifier(DatabaseModifier modifier) {
        dbModifier = modifier;
    }

    @RequestMapping(value = "/accounts/createaccount", method = RequestMethod.POST)
    @ResponseBody
    public String createAccount(@RequestBody String body, HttpServletResponse response) {
        JsonObject parsedJson = JSONUtils.parseJSON(body);
        String email = JSONUtils.getProperty(parsedJson, "emaail");
        String username = JSONUtils.getProperty(parsedJson, "username");
        String password = JSONUtils.getProperty(parsedJson, "password").trim();
        if (dbFetcher.getID(username) != -1) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // fires if the username is already taken
            return "username already taken";
        }
        if (password.length() < 4) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "password too short";
        }
        if (!username.matches("^[a-zA-Z0-9_]*$")) { // username is not alphanumeric
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "usernames must be alphanumeric"
        }
        if (username.length() < 4 || username.length() > 16) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "username length must be in the range 3 to 16 inclusive";
        }
        Thread th = new Thread(() -> {
            String passwordHash = HashUtils.SHA256(password);
            dbModifier.createUser(username, passwordHash, email);
        });
        th.start();
        response.setStatus(HttpServletResponse.SC_OK);
        return "account created";
    }

}
