package Boardfinder.Auth;

import Boardfinder.Auth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Auth application in the Boardfinder App
 * @author Erik
 */
@EnableEurekaClient
@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BoardfinderAuthApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BoardfinderAuthApplication.class, args);
    }

    /**
     * Fills the database with all the snowboards if database is empty. 
     * Not to be used in a real production environment. 
     * This is only used to simplify creation of database data in this school task.
     */
    @Override
    public void run(ApplicationArguments arg) throws Exception {
        userService.createAdminUserInDatabase();
    }

}
