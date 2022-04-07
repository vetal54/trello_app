package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spd.trello.domian.User;

import java.util.UUID;

@Repository
public interface UserRepository extends AbstractRepository<User> {

    @Query("Select TRUE from User  Where email in :email")
    Boolean findUserById(String email);
}
