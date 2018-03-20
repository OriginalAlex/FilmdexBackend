package io.github.originalalex.filmdex.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class API {

    /**
     * Contains the endpoints for the non-user based parts of the site (i.e. completely seperate from user operations)
     */


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "hi";
    }

    @RequestMapping(value = "/basicInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getBasicInformation() {
        return null;
    }

    @RequestMapping(value = "/films/:id", method = RequestMethod.GET)
    @ResponseBody
    public String getSpecificFilm() {
        return null;
    }

    @RequestMapping(value = "/shows/:id", method = RequestMethod.GET)
    @ResponseBody
    public String getSpecificShow() {
        return null;
    }

}
