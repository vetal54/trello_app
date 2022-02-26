package spd.trello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Board;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.BoardService;
import spd.trello.service.WorkspaceService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM board")
class BoardControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Board board;

    private final HttpHeaders headers = new HttpHeaders();

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private BoardService service;

    @Autowired
    private WorkspaceService workspaceService;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void boardSave() throws JsonProcessingException, JSONException {
        Board board = new Board();
        board.setName("name");
        board.setCreateBy("email");
        board.setDescription("description");
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setWorkspaceId(saveWorkspace().getId());

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/board", board, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(board), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void boardFindAllNotEmpty() throws JSONException, JsonProcessingException {
        saveBoard();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/board", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(board)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void boardFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/board", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void boardFindById() throws JSONException, JsonProcessingException {
        saveBoard();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/board/" + board.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(board), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void boardFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/board" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void boardUpdate() throws JsonProcessingException, JSONException {
        saveBoard();
        board.setName("new Name");

        HttpEntity<Board> request = new HttpEntity<>(board, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/board/" + board.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(board), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void boardDeleteById() {
        saveBoard();

        ResponseEntity<String> response = restTemplate.exchange(
                "/board/" + board.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void saveBoard() {
        board = service.create(
                "name",
                "email",
                "description",
                BoardVisibility.PRIVATE,
                saveWorkspace().getId()
        );
    }

    private Workspace saveWorkspace() {
        return workspaceService.create(
                "name",
                "email",
                WorkspaceVisibility.PRIVATE,
                "description"
        );
    }
}
