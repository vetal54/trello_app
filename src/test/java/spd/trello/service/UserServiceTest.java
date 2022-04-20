package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.User;
import spd.trello.exeption.ResourceNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM user_table")
class UserServiceTest {

    @Autowired
    private UserService service;
    @Autowired
    private Helper helper;

    @Test
    void workspaceWasSaved() {
        User savedUser = helper.createUser();
        User userSave = service.findById(savedUser.getId());
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
        List<User> users = service.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    void notEmptyListOfUsersIsReturned() {
        User savedUser = helper.createUser();

        List<User> users = service.findAll();

        assertThat(users).isNotEmpty();
    }

    @Test
    void userWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userWasFoundById() {
        User savedUser = helper.createUser();

        User userFindById = service.findById(savedUser.getId());

        assertThat(userFindById).isEqualTo(savedUser);
    }

    @Test
    void userWasDeleted() {
        User savedUser = helper.createUser();

        service.delete(savedUser.getId());

        assertThatCode(() -> service.findById(savedUser.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userWasUpdated() {
        User savedUser = helper.createUser();
        savedUser.setFirstName("new Name");

        User updatedUser = service.save(savedUser);

        assertThat(updatedUser.getFirstName()).isEqualTo("new Name");
    }
}
