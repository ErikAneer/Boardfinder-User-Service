package Boardfinder.Auth.Service;

import Boardfinder.Auth.Domain.ActiveToken;
import Boardfinder.Auth.Repository.ActiveTokensRepository;
import Boardfinder.Auth.Security.JwtConfig;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service class to handle tokens in the Auth application
 *
 * @author Erik
 */
@Service
public class ActiveTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveTokenService.class);

    private final JwtConfig jwtConfig;
    private final ActiveTokensRepository repo;

    @Autowired
    public ActiveTokenService(JwtConfig jwtConfig, ActiveTokensRepository repo) {
        this.jwtConfig = jwtConfig;
        this.repo = repo;
    }

    /**
     * Saves a generated token
     *
     * @param token
     * @return
     */
    public ActiveToken saveToken(ActiveToken token) {
        LOGGER.info("ActiveTokenService.saveToken called.");
        return repo.save(token);
    }

    /**
     * Deletes a token from database to invalidate that user from future
     * restricted calls.
     *
     * @param request
     * @return
     */
    public boolean deleteToken(HttpServletRequest request) {
        LOGGER.info("ActiveTokenService.deleteToken called.");
        String token = request.getHeader(jwtConfig.getHeader()).replace(jwtConfig.getPrefix(), "");
        return (repo.deleteActiveTokenByToken(token) == 1);
    }

    /**
     * Deletes tokens not used for 2 minutes from database to invalidate those
     * user from future restricted calls.
     */
    @Scheduled(fixedDelay = 120000)
    public void deleteOldTokens() {
        
        LOGGER.info("ActiveTokenService.deleteOldTokens called.");
        int deletedTokens = repo.deleteAllByTimeStampLastUpdatedBefore(LocalDateTime.now().minusMinutes(2));
        LOGGER.info("ActiveTokenService.deleteOldTokens deleted " + deletedTokens + " tokens.");
    }

    /**
     * Checks if an ActiveToken exists in the database in order to be able to
     * grant or deny access. Updates timesStampLastUpdated if exists.
     *
     * @param request as HttpServletRequest
     * @return boolean
     */
    public boolean checkIfActiveToken(HttpServletRequest request) {
        
        LOGGER.info("ActiveTokenService.checkIfActiveToken called.");
        String token = request.getHeader(jwtConfig.getHeader()).replace(jwtConfig.getPrefix(), "");

        return (repo.updateTimeStampForTokenIfItExists(LocalDateTime.now(), token) == 1);

    }

}
