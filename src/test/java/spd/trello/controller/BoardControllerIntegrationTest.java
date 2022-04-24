package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Board;
import spd.trello.domian.User;
import spd.trello.domian.type.BoardVisibility;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM board")
@Sql(statements = "DELETE FROM user_table")
class BoardControllerIntegrationTest extends AbstractIntegrationTest<Board> {

    private final String URL_TEMPLATE = "/board";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        Board board = new Board();
        board.setName("name");
        board.setDescription("description");
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setWorkspaceId(helper.createWorkspace().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, board, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(board.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(board.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(board.getDescription(), getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        Board board = new Board();
        board.setDescription("description");
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setWorkspaceId(helper.createWorkspace().getId());

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, board, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Name should not be empty", getValue(mvc, "$.details.name"))
        );
    }

    @Test
    void delete() throws Exception {
        Board board = helper.createBoard();
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, board.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        Board board = helper.createBoard();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, board.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(board.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(board.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(board.getDescription(), getValue(mvc, "$.description")),
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
        Board firstBoard = helper.createBoard();
        Board secondBoard = helper.createBoard();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Board> boards = helper.getBoardsArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(boards.contains(firstBoard)),
                () -> assertTrue(boards.contains(secondBoard))
        );
    }

    @Test
    void update() throws Exception {
        Board board = helper.createBoard();
        board.setName("new Name");
        board.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, board.getId(), board, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(board.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.name")),
                () -> assertEquals("new Description", getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        Board board = helper.createBoard();
        board.setName("n");
        board.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, board.getId(), board, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 30", getValue(mvc, "$.details.name"))
        );
    }
}
