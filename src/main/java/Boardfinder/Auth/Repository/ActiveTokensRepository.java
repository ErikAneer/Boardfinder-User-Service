
package Boardfinder.Auth.Repository;

import Boardfinder.Auth.Domain.ActiveToken;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for ActiveToken entity class.
 * @author Erik
 */
@Repository
public interface ActiveTokensRepository  extends JpaRepository<ActiveToken, Long> {
    
    /**
     * Deletes all ActiveTokens whose timeStampLastUpdated is older than inparameter timestamp.  
     * @param timeStamp as LocalDateTime
     * @return 
     */
    @Transactional 
    int deleteAllByTimeStampLastUpdatedBefore(LocalDateTime timeStamp);
    
    /**
     * Deletes an ActiveToken by the token String
     * @param token as String
     * @return number of deleted tokens
     */
    @Transactional
    int deleteActiveTokenByToken( String token);
    
     /** Updates the timeStamp for an Active Token to extend it's validity.
      * Returns 0 if the token does not exist and could therefore not be updated. 
     * @param timeNow as LocalDateTime
     * @param token as String
     */
    @Transactional 
    @Modifying
    @Query("UPDATE ActiveToken a SET a.timeStampLastUpdated =  :timeNow WHERE a.token = :token")
    int updateTimeStampForTokenIfItExists(@Param("timeNow") LocalDateTime timeNow, @Param("token") String token);
}
