package io.github.originalalex.filmdex.server.apis;

import io.github.originalalex.filmdex.tmdb.misc.GeneralSearch;
import io.github.originalalex.filmdex.tmdb.movies.MoviesSearch;
import io.github.originalalex.filmdex.tmdb.movies.SearchSpecificMovie;
import io.github.originalalex.filmdex.tmdb.tv.SearchSpecificShow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class GeneralAPI {

    /**
     * Contains the endpoints for the non-user based parts of the site (i.e. completely seperate from user operations)
     */

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

}
