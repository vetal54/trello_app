package spd.trello.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import spd.trello.exeption.JwtAuthenticationException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@Log4j2
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.header}")
    private String authorizationHeader;
    @Value("${spring.jwt.expiration}")
    private long validityInMilliseconds;

    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        log.trace("Entering createToken() method");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);

        log.info("token creation successful");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        log.trace("Entering validateToken() method");
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            log.info("Token validation was successful");
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token check failed. Exception: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        log.trace("Entering getAuthentication() method");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        log.info("Authentication was successful");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        log.trace("Entering getUsername() method");
        log.info("Name received successfully");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        log.trace("Entering resolveToken() method");
        return request.getHeader(authorizationHeader);
    }
}
