package Boardfinder.Auth.Security;

import Boardfinder.Auth.Domain.BoardfinderUser;
import Boardfinder.Auth.Repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Entered loadUserByUsername with username: " + username);
       
            BoardfinderUser appUser = userRepository.findByUsername(username);

            if(appUser != null) {
                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
                     return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
        
            } else {
                 throw new UsernameNotFoundException("Username: " + username + " not found");
            }
    }
}
