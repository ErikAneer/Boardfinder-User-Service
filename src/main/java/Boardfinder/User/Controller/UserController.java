
package Boardfinder.User.Controller;

import Boardfinder.User.Domain.User;
import Boardfinder.User.Service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Erik
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
        private UserService userService;
        
        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }
        
        @PostMapping("/login")
        public String login(@RequestBody Map<String, Object> map) {
        String response= userService.login(map);
            System.out.println("response: " + response);
            return response;
        }
        
        /**
        * Method not in use. Only used in case database does not have any admin user.
        */
        @PostMapping("/register")
        public User registerNewUserAccount(@RequestBody Map<String, Object> map) {
        return userService.registerNewUserAccount(map);
        }
}
