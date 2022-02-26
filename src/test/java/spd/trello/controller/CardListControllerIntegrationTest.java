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
import spd.trello.domian.CardList;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.BoardService;
import spd.trello.service.CardListService;
import spd.trello.service.WorkspaceService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM card_list")
class CardListControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private CardList cardList;

    private final HttpHeaders headers = new HttpHeaders();

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CardListService service;

    @Autowired
    private BoardService boardService;

    @Autowired
    private WorkspaceService workspaceService;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void cardListSave() throws JsonProcessingException, JSONException {
        CardList cardList = new CardList();
        cardList.setName("name");
        cardList.setCreateBy("email");
        cardList.setBoardId(saveBoard().getId());

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/card-list", cardList, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(cardList), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cardListFindAllNotEmpty() throws JSONException, JsonProcessingException {
        saveCardList();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/card-list", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(cardList)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardListFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/card-list", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardListFindById() throws JSONException, JsonProcessingException {
        saveCardList();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/card-list/" + cardList.getId().toString(), String.class);

        System.out.println(response.getBody());
        JSONAssert.assertEquals(mapper.writeValueAsString(cardList), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardListFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/card-list" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void cardListUpdate() throws JsonProcessingException, JSONException {
        saveCardList();

        cardList.setName("new Name");
        HttpEntity<CardList> request = new HttpEntity<>(cardList, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/card-list/" + cardList.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(cardList), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardListDeleteById() {
        saveCardList();

        ResponseEntity<String> response = restTemplate.exchange(
                "/card-list/" + cardList.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void saveCardList() {
        cardList = service.create(
                "name",
                "email",
                saveBoard().getId()
        );
    }

    private Board saveBoard() {
        return boardService.create(
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
