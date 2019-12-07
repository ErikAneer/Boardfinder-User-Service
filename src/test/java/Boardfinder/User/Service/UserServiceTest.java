/*

 */
package Boardfinder.User.Service;

import Boardfinder.User.Domain.User;
import Boardfinder.User.Repository.UserRepository;
import Boardfinder.User.Repository.UserRoleRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Erik
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    public UserServiceTest() {
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void initialize() {
        User testUser = new User();
        testUser.setUsername("adminuser");
        testUser.setPassword(passwordEncoder.encode("adminadmin"));
        testUser.setUserRole(userRoleRepository.findByUserRole("ADMIN"));

    }

    /**
     * Test of registerNewUserAccount method, of class UserService.
     */
    /*
    @Test
    public void testRegisterNewUserAccount() {
        //Given
        Map<String, Object> map = null;
        
        
    }

    /**
     * Test of login method, of class UserService.
     */
    @Test
    public void testLoginShouldLoginAsExpected() {
        // given
        Map<String, Object> map = new HashMap();
        map.put("username", "adminuser");
        map.put("password", "adminadmin");
        String expResult = "adminuser";
        // when
        String result = userService.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }

    @Test
    public void testLoginShouldReturnUserNotExists() {
        // given
        Map<String, Object> map = new HashMap();
        map.put("username", "doesnotexist");
        String expResult = "User does not exist!";
        // when
        String result = userService.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }

    @Test
    public void testLoginShouldReturnBadCredentials() {
        // given
        Map<String, Object> map = new HashMap();
        map.put("username", "adminuser");
        map.put("password", "blahblah");
        String expResult = "Password and/or username does not match.";
        // when
        String result = userService.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }

    @Test
    public void TestUsernameExistsShouldReturnFalse() {
        // given 
        String usernameToTest = "doesNotExistUser";
        // when
        boolean result = userService.usernameExists(usernameToTest);
        //  then
        assertThat(result).isFalse();
    }

    @Test
    public void TestUsernameExistsShouldReturnTrue() {
        // given 
        String usernameToTest = "adminuser";
        // when
        boolean result = userService.usernameExists(usernameToTest);
        //  then
        assertThat(result).isTrue();
    }

}
