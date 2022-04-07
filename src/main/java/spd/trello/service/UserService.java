package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.User;
import spd.trello.exeption.EmailAlreadyExists;
import spd.trello.repository.UserRepository;

@Service
public class UserService extends AbstractDomainService<User, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }

    public User create(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return repository.save(user);
    }

    @Override
    public User save(User user) {
        if (Boolean.TRUE.equals(repository.findUserById(user.getEmail()))) {
            throw new EmailAlreadyExists("Email: " + user.getEmail() + " already exists!");
        }
        return repository.save(user);
    }
}
