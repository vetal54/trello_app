package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.User;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM user_table")
class UserServiceTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private Helper helper;

    @Test
    void workspaceWasSaved() {
        User savedUser = helper.createUser();
        Optional<User> userSave = repository.findById(savedUser.getId());
        assertThat(userSave).isEqualTo(savedUser);
    }

    @Test
    void userNotSavedEmptyName() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("string");
        user.setEmail("string@gmail.com");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    void emptyListOfUsersIsReturned() {
        List<User> users = repository.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    void notEmptyListOfUsersIsReturned() {
        User savedUser = helper.createUser();

        List<User> users = repository.findAll();

        assertThat(users).isNotEmpty();
    }

    @Test
    void userWasNotFoundById() {
        assertThatCode(() -> repository.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userWasFoundById() {
        User savedUser = helper.createUser();

        Optional<User> userFindById = repository.findById(savedUser.getId());

        assertThat(userFindById).isEqualTo(savedUser);
    }

    @Test
    void userWasDeleted() {
        User savedUser = helper.createUser();

        repository.deleteById(savedUser.getId());

        assertThatCode(() -> repository.findById(savedUser.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userWasUpdated() {
        User savedUser = helper.createUser();
        savedUser.setFirstName("new Name");

        User updatedUser = repository.save(savedUser);

        assertThat(updatedUser.getFirstName()).isEqualTo("new Name");
    }
}
