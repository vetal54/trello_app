package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Attachment;
import spd.trello.domian.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM attachment")
@Sql(statements = "DELETE FROM user_table")
class AttachmentControllerIntegrationTest extends AbstractIntegrationTest<Attachment> {

    private final String URL_TEMPLATE = "/attachment";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        Attachment attachment = new Attachment();
        attachment.setName("string");
        attachment.setLink("https://www.instagram.com/");
        attachment.setCardId(helper.createCard().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, attachment, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(attachment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(attachment.getName(), getValue(mvc, "$.name")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        Attachment attachment = new Attachment();
        attachment.setLink("https://www.instagram.com/");
        attachment.setCardId(helper.createCard().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, attachment, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Name should not be empty", getValue(mvc, "$.details.name"))
        );
    }

    @Test
    void delete() throws Exception {
        Attachment attachment = helper.createAttachment();
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, attachment.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        Attachment attachment = helper.createAttachment();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, attachment.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(attachment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(attachment.getName(), getValue(mvc, "$.name")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
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
        Attachment firstAttachment = helper.createAttachment();
        Attachment secondAttachment = helper.createAttachment();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Attachment> attachments = helper.getAttachmentsArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(attachments.contains(firstAttachment)),
                () -> assertTrue(attachments.contains(secondAttachment))
        );
    }

    @Test
    void update() throws Exception {
        Attachment attachment = helper.createAttachment();
        attachment.setName("new Name");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, attachment.getId(), attachment, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(attachment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.name")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        Attachment attachment = helper.createAttachment();
        attachment.setName("n");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, attachment.getId(), attachment, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 100", getValue(mvc, "$.details.name"))
        );
    }
}
