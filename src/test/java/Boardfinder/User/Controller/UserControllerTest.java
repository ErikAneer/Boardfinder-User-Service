/*

 */
package Boardfinder.User.Controller;

import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Erik
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    
    public UserControllerTest() {
    }
    
    @Autowired
   UserController userController;
    
    /**
     * Test of login method, of class UserController. Does log in existing user.
     */
    @Test
    public void testLoginShouldLogInUser() {
         // given
        Map<String, Object> map = new HashMap();
        map.put("username", "adminuser");
        map.put("password", "adminadmin");
        String expResult = "adminuser";
        // when
        String result = userController.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }
    
    /**
     * Test of login method, of class UserController. User does not exist. 
     */
    @Test
    public void testLoginShouldNotLogInUserWhenUSerDoesNotExist() {
         // given
        Map<String, Object> map = new HashMap();
        map.put("username", "doesnotexist");
        String expResult = "User does not exist!";
        // when
        String result = userController.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }
    
    /**
     * Test of login method, of class UserController. User exists but username and password are no match
     */
    @Test
    public void testLoginShouldNotLogInUserWhenBadCredentials() {
         // given
        Map<String, Object> map = new HashMap();
        map.put("username", "adminuser");
        map.put("password", "blahblah");
        String expResult = "Password and/or username does not match.";
        // when
        String result = userController.login(map);
        // then
        assertThat(expResult).isEqualTo(result);
    }
}
