package io.github.originalalex.filmdex.server.apis;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.services.UserService;
import io.github.originalalex.filmdex.tmdb.misc.GeneralSearch;
import io.github.originalalex.filmdex.tmdb.movies.MoviesSearch;
import io.github.originalalex.filmdex.tmdb.movies.SearchSpecificMovie;
import io.github.originalalex.filmdex.tmdb.tv.SearchSpecificShow;
import io.github.originalalex.filmdex.utils.api.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class GeneralAPI {

    /**
     * Contains the endpoints for the non-user based parts of the site (i.e. completely seperate from user operations)
     */

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/basicInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getBasicInformation() {
        return MoviesSearch.getHomepageInformation();
    }

    @RequestMapping(value = "/films/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getSpecificFilm(@PathVariable String id) {
        return SearchSpecificMovie.getSpecificFilm(id);
    }

    @RequestMapping(value = "/shows/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getSpecificShow(@PathVariable String id) {
        return SearchSpecificShow.getSpecificShow(id);
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    @ResponseBody
    public String search(@PathVariable String query) {
        return GeneralSearch.search(query);
    }

    @RequestMapping(value="checkSignedIn", method=RequestMethod.GET)
    @ResponseBody
    public String checkSignedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Token")) {
                token = cookie.getValue();
            }
        }
        if(token.equals("")) return "-1";
        DecodedJWT decodedToken = TokenUtils.decodeToken(token);
        if (decodedToken == null) return "-1";
        long id = decodedToken.getClaim("userId").asLong();
        User user = userService.fetchById(id);
        if (user == null) return "-1";
        return user.getUsername() + " " + id;
    }

}
