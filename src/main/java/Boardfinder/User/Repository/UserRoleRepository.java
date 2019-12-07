package Boardfinder.User.Repository;

import Boardfinder.User.Domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Erik
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByUserRole(String username);
}
