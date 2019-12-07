
package Boardfinder.User.Service;

import Boardfinder.User.Domain.User;
import Boardfinder.User.Domain.UserRole;
import Boardfinder.User.Repository.UserRepository;
import Boardfinder.User.Repository.UserRoleRepository;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Erik
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
     
    public User registerNewUserAccount(Map<String, Object> map){
        
        //How handle this?
        /*
        if (usernameExists(map.get("username").toString())) {
            throw new Exception(
              "There is an account with username: " + map.get("gender").toString());
        }*/
        System.out.println("Entered register method.");
        User user = new User();
        user.setUsername(map.get("username").toString());

        user.setPassword(passwordEncoder.encode(map.get("password").toString()));
        //userRoleRepository.save(new UserRole("ADMIN"));
        //Implement later? OR just create new UserRoleObject?
        user.setUserRole(userRoleRepository.findByUserRole("ADMIN"));
        return  repository.save(user);
    }
    
    
    private boolean usernameExists(String username) {
        User user = repository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    public String login(Map<String, Object> map) {

         if (!usernameExists(map.get("username").toString())) {
           return "User does not exist!";
        }
         User user =  repository.findByUsername(map.get("username").toString());
  
        if(passwordEncoder.matches(map.get("password").toString(), user.getPassword())) {
            return map.get("username").toString();
        }
        //returnera ett dtoUser-objekt med username och userRole? -> behövs här inte för vi har inte säkrat REST än.
        return "Password and/or username does not match.";
    }
}
