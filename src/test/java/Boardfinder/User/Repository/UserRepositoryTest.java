/*

 */
package Boardfinder.User.Repository;

import Boardfinder.User.Domain.User;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Erik
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    
    public UserRepositoryTest() {
    }

 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    
     @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
        
        @Autowired
      private PasswordEncoder passwordEncoder;
    
    
        
    @Test
    public void TestWhenFindByNameSavedAdminUserWillMatch() {
          // given
        User testUser = new User();
        testUser.setUsername("adminuser");
        testUser.setPassword(passwordEncoder.encode("adminadmin"));
        testUser.setUserRole(userRoleRepository.findByUserRole("ADMIN"));
        // when
        User found = userRepository.findByUsername("adminuser");
        // then
        assertThat(testUser.getUsername()).isEqualTo(found.getUsername());
        assertThat(testUser.getUserRole().getUserRole()).isEqualTo(found.getUserRole().getUserRole());
        passwordEncoder.matches("adminadmin", found.getPassword());
    }
    
}
    

