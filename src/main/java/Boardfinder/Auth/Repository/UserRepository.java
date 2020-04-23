package Boardfinder.Auth.Repository;

import Boardfinder.Auth.Domain.BoardfinderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Erik
 */
@Repository
public interface UserRepository extends JpaRepository<BoardfinderUser, Long> {

    BoardfinderUser findByUsername(String username);
}
