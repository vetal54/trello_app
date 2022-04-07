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
import spd.trello.domian.Attachment;
import spd.trello.domian.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM attachment")
class AttachmentControllerIntegrationTest {

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
    void attachmentSave() throws JsonProcessingException, JSONException {
        Attachment attachment = new Attachment();
        attachment.setName("string");
        attachment.setLink("https://www.instagram.com/");
        attachment.setCreateBy("string@gmail.com");
        attachment.setCardId(helper.createCard().getId());

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/attachment", attachment, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(attachment), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void attachmentFindAllNotEmpty() throws JSONException, JsonProcessingException {
        Attachment attachment = helper.createAttachment();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/attachment", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(attachment)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void attachmentFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/attachment", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void attachmentFindById() throws JSONException, JsonProcessingException {
        Attachment attachment = helper.createAttachment();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/attachment/" + attachment.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(attachment), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void attachmentFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/attachment/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void attachmentUpdate() throws JsonProcessingException, JSONException {
        Attachment attachment = helper.createAttachment();
        attachment.setName("new Name");

        HttpEntity<Attachment> request = new HttpEntity<>(attachment, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/attachment/" + attachment.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(headers), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void attachmentDeleteById() {
        Attachment attachment = helper.createAttachment();

        ResponseEntity<String> response = restTemplate.exchange(
                "/attachment/" + attachment.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
