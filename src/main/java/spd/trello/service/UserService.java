package spd.trello.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domian.User;
import spd.trello.exeption.EmailAlreadyExists;
import spd.trello.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) throws EmailAlreadyExists {

        if (checkIfUserExist(user.getEmail())) {
            throw new EmailAlreadyExists("Email already exists");
        }
        encodePassword(user);
        return userRepository.save(user);
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
