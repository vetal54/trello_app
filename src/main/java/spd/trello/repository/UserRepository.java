package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domian.User;


@Repository
public interface UserRepository extends AbstractRepository<User> {

    User findByEmail(String email);
}
