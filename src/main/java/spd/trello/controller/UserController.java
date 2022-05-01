package spd.trello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.AuthenticationRequestDTO;
import spd.trello.domian.User;
import spd.trello.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends AbstractDomainController<User, UserService> {

    public UserController(UserService userService) {
        super(userService);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registration(@Valid @RequestBody User resource) throws JsonProcessingException {
        User result = service.register(resource);
        log.info("Registration was successful");
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        Map<Object, Object> response = service.authenticate(request);
        log.info("Authentication was successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        log.info("Logout was successful");
    }
}
