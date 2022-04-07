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
import spd.trello.domian.CardList;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM card_list")
class CardListControllerIntegrationTest {

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
    void cardListSave() {
        CardList cardList = new CardList();
        cardList.setName("name");
        cardList.setCreateBy("email@gmail.com");
        cardList.setBoardId(helper.createBoard().getId());

        ResponseEntity<CardList> response = restTemplate
                .postForEntity(getRootUrl() + "/card-list", cardList, CardList.class);

        assertEquals(cardList, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cardListFindAllNotEmpty() throws JSONException, JsonProcessingException {
        CardList cardList = helper.createCardList();

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
        CardList cardList = helper.createCardList();

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
        CardList cardList = helper.createCardList();

        cardList.setName("new Name");
        HttpEntity<CardList> request = new HttpEntity<>(cardList, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/card-list/" + cardList.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(cardList), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cardListDeleteById() {
        CardList cardList = helper.createCardList();

        ResponseEntity<String> response = restTemplate.exchange(
                "/card-list/" + cardList.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
