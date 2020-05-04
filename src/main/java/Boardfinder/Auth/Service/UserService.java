package Boardfinder.Auth.Service;

import Boardfinder.Auth.Domain.BoardfinderUser;
import Boardfinder.Auth.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling users.
 * @author Erik
 */
@Service
public class UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
     
    /**
     * Method not to be used in production. Only used in dev and in case database does not have any admin user.
     * An ordinary user is also created just to be able to demonstrate role based access during school presentaion. 
     * Exception will be thrown if try to use more than once as unique restraint exists. 
     */
    public void registerNewUserAccount(){
        LOGGER.info("UserService.registerNewUserAccount called to create user \"admin\"");
        BoardfinderUser userAdmin = new BoardfinderUser("admin", passwordEncoder.encode("admin"), "ADMIN");
        repository.save(userAdmin);
        BoardfinderUser userUser = new BoardfinderUser("user", passwordEncoder.encode("user"), "USER");
        repository.save(userUser);
    }
    
    /**
     * Method not to be used in production. Checks if there are any users in the user database. If not it will create an admin user. 
     * Only used in dev and in case database does not have any admin user.
     */
    public void createAdminUserInDatabase() {
         if (repository.findAll().isEmpty()) {
             registerNewUserAccount();
        
    }
    }
    
}