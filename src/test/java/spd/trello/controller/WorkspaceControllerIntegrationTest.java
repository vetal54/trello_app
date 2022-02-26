package spd.trello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.WorkspaceService;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM workspace")
class WorkspaceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Workspace workspace;

    private final HttpHeaders headers = new HttpHeaders();

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WorkspaceService service;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void workspaceSave() throws JsonProcessingException, JSONException {
        Workspace workspace = new Workspace();
        workspace.setName("name");
        workspace.setCreateBy("email");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");

        ResponseEntity<String> response = restTemplate
                .postForEntity(getRootUrl() + "/workspace", workspace, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(workspace), response.getBody(), false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void workspaceFindAllNotEmpty() throws JSONException, JsonProcessingException {
        saveWorkspace();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/workspace", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(workspace)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void workspaceFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/workspace", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void workspaceFindById() throws JSONException, JsonProcessingException {
        saveWorkspace();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/workspace/" + workspace.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(workspace), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void workspaceFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/workspace/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void workspaceUpdate() throws JsonProcessingException, JSONException {
        saveWorkspace();
        workspace.setName("new Name");

        HttpEntity<Workspace> request = new HttpEntity<>(workspace, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/workspace/" + workspace.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(workspace), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void workspaceDeleteById() {
        saveWorkspace();

        ResponseEntity<String> response = restTemplate.exchange(
                "/workspace/" + workspace.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void saveWorkspace() {
        workspace = service.create(
                "name",
                "email",
                WorkspaceVisibility.PRIVATE,
                "description"
        );
    }
}


