package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Comment;
import spd.trello.domian.Member;
import spd.trello.domian.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM comment")
@Sql(statements = "DELETE FROM user_table")
class CommentControllerIntegrationTest extends AbstractIntegrationTest<Comment> {

    private final String URL_TEMPLATE = "/comment";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = new Comment();
        comment.setContext("context text! Hello!");
        comment.setCardId(helper.createCard().getId());
        comment.setMemberId(member.getId());

        MvcResult mvc = super.create(URL_TEMPLATE, comment, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(comment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(comment.getContext(), getValue(mvc, "$.context")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = new Comment();
        comment.setCardId(helper.createCard().getId());
        comment.setMemberId(member.getId());

        MvcResult mvc = super.create(URL_TEMPLATE, comment, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Context should not be empty", getValue(mvc, "$.details.context"))
        );
    }

    @Test
    void delete() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = helper.createComment(member);

        MvcResult mvc = super.delete(URL_TEMPLATE, comment.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = helper.createComment(member);

        MvcResult mvc = super.getById(URL_TEMPLATE, comment.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(comment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(comment.getContext(), getValue(mvc, "$.context")),
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
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment firstComment = helper.createComment(member);
        Comment secondComment = helper.createComment(member);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Comment> comments = helper.getCommentsArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(comments.contains(firstComment)),
                () -> assertTrue(comments.contains(secondComment))
        );
    }

    @Test
    void update() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = helper.createComment(member);
        comment.setContext("new Context");

        MvcResult mvc = super.update(URL_TEMPLATE, comment.getId(), comment, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(comment.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Context", getValue(mvc, "$.context")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        String token = helper.createUserAdminAndGetToken(user);

        Comment comment = helper.createComment(member);
        comment.setContext("new Con");

        MvcResult mvc = super.update(URL_TEMPLATE, comment.getId(), comment, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 10 and 100", getValue(mvc, "$.details.context"))
        );
    }
}
