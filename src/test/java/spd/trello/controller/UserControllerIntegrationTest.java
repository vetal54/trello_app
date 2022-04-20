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
import spd.trello.domian.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM user_table")
class UserControllerIntegrationTest {

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
    void userSave() throws JsonProcessingException, JSONException {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setLastName("string");
        user.setFirstName("string");
        user.setPassword("admin");

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/user", user, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(user), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void userFindAllNotEmpty() throws JSONException, JsonProcessingException {
        User user = helper.createUser();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/user", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(user)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void userFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/user", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void userFindById() throws JSONException, JsonProcessingException {
        User user = helper.createUser();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/user/" + user.getId().toString(), String.class);

        System.out.println(mapper.writeValueAsString(user));
        System.out.println(response.getBody());

        JSONAssert.assertEquals(mapper.writeValueAsString(user), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void userFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/user/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void userUpdate() throws JsonProcessingException, JSONException {
        User user = helper.createUser();
        user.setFirstName("new Name");

        HttpEntity<User> request = new HttpEntity<>(user, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/user/" + user.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(this.headers), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void userDeleteById() {
        User user = helper.createUser();

        ResponseEntity<String> response = restTemplate.exchange(
                "/user/" + user.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
