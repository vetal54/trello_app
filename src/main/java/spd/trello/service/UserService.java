package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.User;
import spd.trello.repository.UserRepository;

@Service
public class UserService extends AbstractDomainService<User, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }
}
