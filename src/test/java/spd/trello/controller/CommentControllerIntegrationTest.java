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
import spd.trello.domian.Comment;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM comment")
class CommentControllerIntegrationTest {

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
    void commentSave() throws JsonProcessingException, JSONException {
        Comment comment = new Comment();
        comment.setContext("context text! Hello!");
        comment.setCreateBy("email@gmail.com");
        comment.setCardId(helper.createCard().getId());
        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/comment", comment, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(comment), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void commentFindAllNotEmpty() throws JSONException, JsonProcessingException {
        Comment comment = helper.createComment();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/comment", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(comment)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void commentFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/comment", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void commentFindById() throws JSONException, JsonProcessingException {
        Comment comment = helper.createComment();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/comment/" + comment.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(comment), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void commentFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/comment/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void commentUpdate() throws JsonProcessingException, JSONException {
        Comment comment = helper.createComment();
        comment.setContext("new Context");

        HttpEntity<Comment> request = new HttpEntity<>(comment, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/comment/" + comment.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(comment), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void commentDeleteById() {
        Comment comment = helper.createComment();

        ResponseEntity<String> response = restTemplate.exchange(
                "/comment/" + comment.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
