/*

 */
package Boardfinder.User.Repository;

import Boardfinder.User.Domain.User;
import Boardfinder.User.Domain.UserRole;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class UserRoleRepositoryTest {
    
    public UserRoleRepositoryTest() {
    }
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Test of findByUserRole method, of class UserRoleRepository.
     */
    @Test
    public void testFindByUserRole() {
                          // given
        UserRole testUseRole = new UserRole();
        // when
        testUseRole.setUserRole("ADMIN");
        // then
        assertThat(testUseRole.getUserRole()).isEqualTo(userRoleRepository.findByUserRole("ADMIN").getUserRole());
    }

}
