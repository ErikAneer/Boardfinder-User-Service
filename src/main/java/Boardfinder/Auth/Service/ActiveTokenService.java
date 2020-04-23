/*

 */
package Boardfinder.Auth.Service;

import Boardfinder.Auth.Domain.ActiveToken;
import Boardfinder.Auth.Repository.ActiveTokensRepository;
import Boardfinder.Auth.Repository.CustomTokenRepository;
import Boardfinder.Auth.Security.JwtConfig;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service class to handle tokens in the Auth application
 * @author Erik
 */
@Service
public class ActiveTokenService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveTokenService.class);
    
    private final JwtConfig jwtConfig;
    private final ActiveTokensRepository repo;
    private final CustomTokenRepository customRepo;
    
    @Autowired    
    public ActiveTokenService(JwtConfig jwtConfig, ActiveTokensRepository repo, CustomTokenRepository customRepo) {
        this.jwtConfig = jwtConfig;        
        this.repo = repo;
        this.customRepo = customRepo;
    }

    /**
     * Saves a generated token
     * @param token
     * @return 
     */
    public ActiveToken saveToken(ActiveToken token) {
        LOGGER.info("ActiveTokenService.saveToken called.");
        return repo.save(token);
    }
    
    /**
     * Deletes a token from database to invalidate that user from future restricted calls.
     * @param request
     * @return 
     */
    public boolean deleteToken(HttpServletRequest request) {
        LOGGER.info("ActiveTokenService.deleteToken called.");
        String token = request.getHeader(jwtConfig.getHeader()).replace(jwtConfig.getPrefix(), ""); 
        return customRepo.deleteToken(token);
    }
    /**
     * Deletes tokens not used for 2 minutes from database to invalidate those user from future restricted calls.
     */
    @Scheduled(fixedDelay = 60000)
    public void deleteOldTokens() {
        LOGGER.info("ActiveTokenService.deleteOldTokens called.");
        customRepo.deleteOldTokens();
    }
    
    /** 
     * Checks if an ActiveToken exists in the database in order to be able to grant or deny access.
     * @param request as HttpServletRequest
     * @return boolean
     * @throws AccessDeniedException when an ActiveToken is missing. 
     */
    public boolean checkIfActiveToken(HttpServletRequest request) throws AccessDeniedException {
            LOGGER.info("ActiveTokenService.checkIfActiveToken called.");
            
            Long activeTokenInDB = null;
           String token = request.getHeader(jwtConfig.getHeader()).replace(jwtConfig.getPrefix(), "");  
           activeTokenInDB =  customRepo.getActiveTokenIdByToken(token);   
            
           if(activeTokenInDB == null){
               LOGGER.info("ActiveTokenService.checkIfActiveToken could not find token.");
                return false;
            } 
            
           customRepo.setTimeStampLastUpdatedByToken(LocalDateTime.now(), activeTokenInDB);
           LOGGER.info("ActiveTokenService.checkIfActiveToken could validate token.");
           return true;
        }
}
