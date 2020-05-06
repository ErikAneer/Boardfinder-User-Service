package Boardfinder.Auth.Controller;

import Boardfinder.Auth.Domain.BoardfinderUser;
import Boardfinder.Auth.Domain.UserCredentials;
import Boardfinder.Auth.Security.JwtConfig;
import Boardfinder.Auth.Security.JwtResponse;
import Boardfinder.Auth.Service.ActiveTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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



    private final JwtConfig jwtConfig;
    
    @Autowired
    public UserController(ActiveTokenService tokenService, JwtConfig jwtConfig) {
        this.tokenService = tokenService;
        this.jwtConfig = jwtConfig;
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
        /*
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }*/
        return " \"{\"success\":true, \"data\": [{ \"code\": \"SUCCESS\",\"message\": \"User successfully logged out\"}]}\"";
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
    
    @RequestMapping("/sucesslogin")
    public String myMethod(ModelMap model){
        System.out.println("Success");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoardfinderUser userDetails = (BoardfinderUser)auth.getPrincipal();
        model.addAttribute("userName", userDetails.getUsername());
        model.addAttribute("role", userDetails.getRole());
        System.out.println(model.toString());
        return model.toString();
    }
    
    
    
    /*
    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response) {

            
            
        
                try {
                    
                    UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
        
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		//String jwt = jwtUtils.generateJwtToken(authentication);
                Long now = System.currentTimeMillis();
                String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
		
		//UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = authentication.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(token, creds.getUsername(), roles));
												 
		} catch (IOException e) {
            throw new RuntimeException(e);
        }										 
	}
    */
}
