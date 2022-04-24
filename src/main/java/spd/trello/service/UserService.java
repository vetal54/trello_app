package spd.trello.service;

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
            throw new EmailAlreadyExists("Email already exists");
        }
        encodePassword(user);
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
            return response;
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean checkIfUserExist(String email) {
        return repository.findByEmail(email) != null;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
