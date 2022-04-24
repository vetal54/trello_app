package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements = "DELETE FROM user_table")
class UserControllerIntegrationTest extends AbstractIntegrationTest<User> {

    private final String URL_TEMPLATE = "/user";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setLastName("string");
        user.setFirstName("string");
        user.setPassword("admin");

        MvcResult mvc = super.register(URL_TEMPLATE + "/register", user);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(user.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(user.getFirstName(), getValue(mvc, "$.firstName"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword("admin");

        MvcResult mvc = super.register(URL_TEMPLATE + "/register", user);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("First name should not be empty", getValue(mvc, "$.details.firstName")),
                () -> assertEquals("Last name should not be empty", getValue(mvc, "$.details.lastName"))
        );
    }

    @Test
    void delete() throws Exception {
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, user.getId(), token);

        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, user.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(user.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(user.getFirstName(), getValue(mvc, "$.firstName"))
        );
    }

    @Test
    void findByIdFailed() throws Exception {
        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, UUID.randomUUID(), token);

        assertEquals(HttpStatus.NOT_FOUND.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findAll() throws Exception {
        User firstUser = helper.createUser();

        String token = helper.createUserAdminAndGetToken(firstUser);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<User> users = helper.getUsersArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(users.contains(firstUser))
        );
    }

    @Test
    void update() throws Exception {
        User user = helper.createUser();
        user.setFirstName("new Name");

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, user.getId(), user, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(user.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.firstName"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        User user = helper.createUser();
        user.setFirstName("n");

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, user.getId(), user, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 30", getValue(mvc, "$.details.firstName"))
        );
    }
}
