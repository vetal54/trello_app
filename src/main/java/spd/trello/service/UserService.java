package spd.trello.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domian.AuthenticationRequestDTO;
import spd.trello.domian.User;
import spd.trello.exeption.EmailAlreadyExists;
import spd.trello.exeption.JwtAuthenticationException;
import spd.trello.repository.UserRepository;
import spd.trello.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UserService extends AbstractDomainService<User, UserRepository> {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User register(User user) throws EmailAlreadyExists {
        if (checkIfUserExist(user.getEmail())) {
            log.error("Email already exists: {}", user.getEmail());
            throw new EmailAlreadyExists("Email already exists");
        }
        encodePassword(user);
        log.info("User registered successfully");
        return repository.save(user);
    }

    public Map<Object, Object> authenticate(AuthenticationRequestDTO authentication) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getEmail(), authentication.getPassword()));
            User user = findByEmail(authentication.getEmail());
            String token = jwtTokenProvider.createToken(authentication.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", authentication.getEmail());
            response.put("token", token);
            log.info("User authenticated successfully");
            return response;
        } catch (AuthenticationException e) {
            log.error("User not authenticated");
            throw new JwtAuthenticationException("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    public User findByEmail(String email) {
        log.info("Search for a user by email");
        return repository.findByEmail(email);
    }

    public boolean checkIfUserExist(String email) {
        return repository.findByEmail(email) != null;
    }

    private void encodePassword(User user) {
        log.info("Encode password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
