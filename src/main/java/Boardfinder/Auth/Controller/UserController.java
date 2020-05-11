package Boardfinder.Auth.Controller;

import Boardfinder.Auth.Service.ActiveTokenService;

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

    private final ActiveTokenService tokenService;

    @Autowired
    public UserController(ActiveTokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Logs out a user by it´s token
     *
     * @param request
     * @param response
     * @return String
     */
    @GetMapping("/logout")
    public String fetchSignOutFromClient(HttpServletRequest request, HttpServletResponse response) {
        tokenService.deleteToken(request);
        
        return "User successfully logged out";
    }

    /**
     * Confirms if a user is logged in or not by the token. But if no token
     * exists the Zuul API Gateway should always stop the request to this path
     * so it should never be reached if no token.
     *
     * @param request
     * @param response
     * @return booleann
     */
    @GetMapping("/isloggedin")
    public boolean checkIfLoggedInUser(HttpServletRequest request, HttpServletResponse response) {

        return tokenService.checkIfActiveToken(request);
    }
}
