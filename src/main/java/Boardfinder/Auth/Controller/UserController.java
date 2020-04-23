/*

 */
package Boardfinder.Auth.Controller;

import Boardfinder.Auth.Domain.BoardfinderUser;
import Boardfinder.Auth.Service.ActiveTokenService;
import Boardfinder.Auth.Service.UserService;
import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for the Auth application to hande manual logout and
 * isLoggedIn check.
 *
 * @author Erik
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ActiveTokenService tokenService;

    @Autowired
    public UserController(UserService userService, ActiveTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * Method not in use. Only used in case database does not have any admin
     * user.
     */
    /*@GetMapping("/register")
    public BoardfinderUser registerNewUserAccount() {
        System.out.println("Enterered register");
        return userService.registerNewUserAccount();
    }*/
    /**
     * Logs out a user by it´s token
     *
     * @param request
     * @param response
     * @return String
     */
    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {

        tokenService.deleteToken(request);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return " \"{\"success\":true, \"data\": [{ \"code\": \"SUCCESS\",\"message\": \"User successfully logged out\"}]}\"";
    }

    /**
     * Confirms if a user is logged in or not by the token. No valid token found
     * = not logged in and throws an AccessDeniedException
     *
     * @param request
     * @param response
     * @return String
     * @throws AccessDeniedException
     */
    @GetMapping("/isloggedin")
    public String checkIfLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException {

        if (!tokenService.checkIfActiveToken(request)) {
            throw new AccessDeniedException("Valid token does not exist");
        }
        return "{\"success\":true, \"data\": [{ \"code\": \"SUCCESS\",\"message\": \"User is logged in.\"}]}";
    }

}
