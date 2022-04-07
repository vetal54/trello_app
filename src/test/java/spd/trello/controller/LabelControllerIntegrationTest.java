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
import spd.trello.domian.Label;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM label")
class LabelControllerIntegrationTest {

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
    void labelSave() throws JsonProcessingException, JSONException {
        Label label = new Label();
        label.setName("string");
        label.setColor("color");
        label.setCardId(helper.createCard().getId());

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/label", label, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(label), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void labelFindAllNotEmpty() throws JSONException, JsonProcessingException {
        Label label = helper.createLabel();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/label", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(label)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void labelFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/label", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void labelFindById() throws JSONException, JsonProcessingException {
        Label label = helper.createLabel();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/label/" + label.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(label), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void labelFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/label/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void labelUpdate() throws JsonProcessingException, JSONException {
        Label label = helper.createLabel();
        label.setName("new Name");

        HttpEntity<Label> request = new HttpEntity<>(label, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/label/" + label.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(this.headers), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void labelDeleteById() {
        Label label = helper.createLabel();

        ResponseEntity<String> response = restTemplate.exchange(
                "/label/" + label.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
