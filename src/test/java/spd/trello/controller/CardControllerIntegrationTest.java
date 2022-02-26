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
import spd.trello.domian.*;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.BoardService;
import spd.trello.service.CardListService;
import spd.trello.service.CardService;
import spd.trello.service.WorkspaceService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM card")
class CardControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Card card;

    private final HttpHeaders headers = new HttpHeaders();

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CardService service;

    @Autowired
    private CardListService cardListService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private WorkspaceService workspaceService;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void cardSave() throws JsonProcessingException, JSONException {
        Card card = new Card();
        card.setName("name");
        card.setCreateBy("email");
        card.setDescription("description");
        card.setCardListId(saveCardList().getId());
        card.setReminder(new Reminder());
        CheckList checkList = new CheckList();
        checkList.setName("name");
        card.setCheckList(checkList);

        ResponseEntity<String> response = restTemplate.postForEntity(
                getRootUrl() + "/card", card, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(card), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cardFindAllNotEmpty() throws JSONException, JsonProcessingException {
        saveCard();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/card", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(card)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/card", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardFindById() throws JSONException, JsonProcessingException {
        saveCard();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/card/" + card.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(card), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/card" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void cardUpdate() throws JsonProcessingException, JSONException {
        saveCard();
        card.setName("new Name");

        HttpEntity<Card> request = new HttpEntity<>(card, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/card/" + card.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(card), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardDeleteById() {
        saveCard();

        ResponseEntity<String> response = restTemplate.exchange(
                "/card/" + card.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void saveCard() {
        card = service.create(
                "name",
                "email",
                "description",
                saveCardList().getId()
        );
    }

    private CardList saveCardList() {
        return cardListService.create(
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
