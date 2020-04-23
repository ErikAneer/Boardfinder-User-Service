
package Boardfinder.Auth.Repository;

import Boardfinder.Auth.Domain.ActiveToken;
import Boardfinder.Auth.Security.JwtConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ActiveToken entity class.
 * @author Erik
 */
@Repository
public interface ActiveTokensRepository  extends JpaRepository<ActiveToken, Long> {

}
