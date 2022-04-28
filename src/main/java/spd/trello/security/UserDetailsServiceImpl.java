package spd.trello.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spd.trello.domian.User;
import spd.trello.repository.UserRepository;

@Service("userDetailsServiceImpl")
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.trace("Entering loadUserByUsername() method");
        User user = userRepository.findByEmail(email);
        log.info("Authenticating successfully");
        return SecurityUser.fromUser(user);
    }
}
