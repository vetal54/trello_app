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
import spd.trello.Helper;
import spd.trello.domian.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM card")
class CardControllerIntegrationTest {

    @Autowired
    private Helper helper;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final HttpHeaders headers = new HttpHeaders();
    private final ObjectMapper mapper = new ObjectMapper();

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void cardSave() {
        Card card = new Card();
        card.setName("string name");
        card.setCreateBy("email@gmail.com");
        card.setDescription("new description");
        card.setCardListId(helper.createCardList().getId());
        Reminder reminder = new Reminder();
        reminder.setStart(LocalDateTime.now().plus(Duration.of(5, ChronoUnit.MINUTES)));
        reminder.setRemindOn(LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES)));
        reminder.setEnd(LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES)));
        card.setReminder(reminder);
        CheckList checkList = new CheckList();
        checkList.setName("string");
        card.setCheckList(checkList);

        ResponseEntity<Card> response = restTemplate.postForEntity(
                getRootUrl() + "/card", card, Card.class);

        assertEquals(card, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cardFindAllNotEmpty() throws JSONException, JsonProcessingException {
        Card card = helper.createCard();

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
        Card card = helper.createCard();

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
        Card card = helper.createCard();
        card.setName("new Name");

        HttpEntity<Card> request = new HttpEntity<>(card, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/card/" + card.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(card), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardDeleteById() {
        Card card = helper.createCard();

        ResponseEntity<String> response = restTemplate.exchange(
                "/card/" + card.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
