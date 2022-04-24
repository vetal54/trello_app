package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Label;
import spd.trello.domian.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM label")
@Sql(statements = "DELETE FROM user_table")
class LabelControllerIntegrationTest extends AbstractIntegrationTest<Label> {

    private final String URL_TEMPLATE = "/label";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        Label label = new Label();
        label.setName("string");
        label.setColor("color");
        label.setCardId(helper.createCard().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, label, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(label.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(label.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(label.getColor(), getValue(mvc, "$.color"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        Label label = new Label();
        label.setColor("color");
        label.setCardId(helper.createCard().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, label, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Name should not be empty!", getValue(mvc, "$.details.name"))
        );
    }

    @Test
    void delete() throws Exception {
        Label label = helper.createLabel();
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, label.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        Label label = helper.createLabel();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, label.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(label.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(label.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(label.getColor(), getValue(mvc, "$.color"))
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
        Label firstLabel = helper.createLabel();
        Label secondLabel = helper.createLabel();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Label> labels = helper.getLabelsArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(labels.contains(firstLabel)),
                () -> assertTrue(labels.contains(secondLabel))
        );
    }

    @Test
    void update() throws Exception {
        Label label = helper.createLabel();
        label.setName("new Name");
        label.setColor("new Color");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, label.getId(), label, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(label.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.name")),
                () -> assertEquals("new Color", getValue(mvc, "$.color"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        Label label = helper.createLabel();
        label.setName("n");
        label.setColor("n");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, label.getId(), label, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 30", getValue(mvc, "$.details.name")),
                () -> assertEquals("size must be between 5 and 30", getValue(mvc, "$.details.color"))
        );
    }
}
