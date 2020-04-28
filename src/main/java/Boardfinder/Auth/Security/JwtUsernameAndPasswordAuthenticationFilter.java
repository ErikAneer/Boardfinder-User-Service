package Boardfinder.Auth.Security;

import Boardfinder.Auth.Domain.ActiveToken;
import Boardfinder.Auth.Domain.UserCredentials;
import Boardfinder.Auth.Service.ActiveTokenService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Filter class that tries to authenticate incoming http requests for login.
 * Generates ans saves token upon successful authentication. Modified code from
 * Omar El Gabry
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;

    private final JwtConfig jwtConfig;

    private final ActiveTokenService tokenService;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig, ActiveTokenService tokenService) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
        this.tokenService = tokenService;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    /**
     * Authenticates a user from the incoming request using the request's user
     * credentials.
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException if user cannot be authenticated.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a token for the user upon successful authentication. Does also
     * save the generated token to be able to later verify active users.
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        tokenService.saveToken(new ActiveToken(token, LocalDateTime.now(), LocalDateTime.now()));
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }
}
