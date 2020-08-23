package io.github.originalalex.filmdex.server.apis;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.originalalex.filmdex.exceptions.UserAlreadyExistsException;
import io.github.originalalex.filmdex.server.database.models.Post;
import io.github.originalalex.filmdex.server.database.models.Rating;
import io.github.originalalex.filmdex.server.database.models.User;
import io.github.originalalex.filmdex.server.database.repositories.PostRepository;
import io.github.originalalex.filmdex.server.database.services.PostService;
import io.github.originalalex.filmdex.server.database.services.UserService;
import io.github.originalalex.filmdex.exceptions.EmailAlreadyExistsException;
import io.github.originalalex.filmdex.server.dto.*;
import io.github.originalalex.filmdex.utils.api.TokenUtils;
import io.github.originalalex.filmdex.utils.io.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/users")
@CrossOrigin(
        allowCredentials = "true",
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
)
public class AccountsAPI {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody RequestResult create(@RequestBody @Valid UserDto user, BindingResult bindingResult, HttpServletResponse response) {
        RequestResult result = new RequestResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                result.addResult(error.getDefaultMessage());
            }
            response.setStatus(400);
            return result;
        }
        try {
            userService.registerNewUser(user);
        } catch(EmailAlreadyExistsException e) {
            result.addResult("That email is already in use");
            response.setStatus(409); // conflict status
            return result;
        } catch(UserAlreadyExistsException e) {
            result.addResult("That username is already taken");
            response.setStatus(409);
            return result;
        }
        result.addResult("Registered user");
        return result;
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
            cookie.setPath("/");
            cookie.setMaxAge((int) TimeUnit.DAYS.toMillis(1));
            response.addCookie(cookie);
            return "success";
        }
        return "failure";
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    public @ResponseBody String signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "signed out";
    }

    @RequestMapping(value = "/submitPost", method = RequestMethod.POST)
    public @ResponseBody String post(@RequestBody @Valid PostDto postDto, HttpServletRequest request) {
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
        User poster = userService.fetchById(decodedToken.getClaim("userId").asLong());
        if (poster == null) return "invalid poster id";
        Post post = postService.createPost(poster, postDto.getThread(), (postDto.getReplyingTo() == null) ? -1 : postDto.getReplyingTo(), postDto.getBody());
        if (post != null) {
            return "created";
        }
        return "failed";
    }

    @RequestMapping(value = "/fetchPostsByThread", method = RequestMethod.GET)
    public @ResponseBody List<Post> fetchPostsByThread(@RequestParam("thread") String thread) {
        List<Post> posts = postService.getPostsByThread(thread);
        return posts;
    }

    @RequestMapping(value="/upvote", method = RequestMethod.POST)
    public @ResponseBody String upvote(HttpServletRequest request, @RequestBody RatingDTO ratingDTO) {
        Cookie[] cookies = request.getCookies();
        System.out.println("---------------------NEW REQUEST------------------------------------");
        System.out.println("HERE: " + ratingDTO.isUpvote());
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
        Post post = postService.fetchById(ratingDTO.getPostID());

        List<Rating> ratings = post.getRatings();
        Rating toDelete = null;
        for (Rating r : ratings) {
            System.out.println(r.isUpvote());
            if (r.getRaterId() == id) {
                toDelete = r;
                break;
            }
        }
        if (toDelete != null) {
            if (toDelete.isUpvote() == ratingDTO.isUpvote()) {
                ratings.remove(toDelete);
                postRepository.save(post);
                System.out.println("-----------removed-----------");
                return "removed vote";
            } else {
                ratings.remove(toDelete);
            }
        }
        Rating rating = new Rating(post, id, ratingDTO.isUpvote());
        ratings.add(rating);
        postRepository.save(post);
        return "success";
    }

    @RequestMapping(value = "/signOutNow", method = RequestMethod.POST)
    public void signout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @RequestMapping(
            value = "/*",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
