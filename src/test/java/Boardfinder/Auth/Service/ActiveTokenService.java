
package Boardfinder.Auth.Service;

import Boardfinder.Auth.Domain.ActiveToken;
import Boardfinder.Auth.Repository.ActiveTokensRepository;
import Boardfinder.Auth.Repository.CustomTokenRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Erik
 */
@Service
public class ActiveTokenService {
    
    
        private ActiveTokensRepository repo;
        
        private CustomTokenRepository customRepo;
          
        @Autowired 
        public ActiveTokenService ( ActiveTokensRepository repo, CustomTokenRepository customRepo) {
                this.repo = repo;
                this.customRepo = customRepo;
        }
        
        public ActiveToken save(ActiveToken token) {
            System.out.println("Tries to save token: " + token.getToken());
            return repo.save(token);
        }
        
        @Scheduled(fixedDelay=60000)
        public void doSomething() {
            System.out.println("doSomething called");
           customRepo.deleteOldTokens();
        }
}
