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
import spd.trello.domian.Member;
import spd.trello.domian.User;
import spd.trello.domian.type.Role;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "DELETE FROM member")
class MemberControllerIntegrationTest {

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
    void memberSave() {
        User user = helper.createUser();
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setUserId(user.getId());

        ResponseEntity<Member> response = restTemplate
                .postForEntity(getRootUrl() + "/member", member, Member.class);

        assertEquals(member, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void memberFindAllNotEmpty() throws JSONException, JsonProcessingException {
        Member member = helper.createMember();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/member", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(List.of(member)), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberFindAllEmptyList() throws JsonProcessingException, JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/member", HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(Collections.EMPTY_LIST), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberFindById() throws JSONException, JsonProcessingException {
        Member member = helper.createMember();

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/member/" + member.getId().toString(), String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(member), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberFindByIdNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getRootUrl() + "/member/" + UUID.randomUUID(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void userUpdate() throws JsonProcessingException, JSONException {
        Member member = helper.createMember();
        member.setRole(Role.GUEST);

        HttpEntity<Member> request = new HttpEntity<>(member, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
                "/member/" + member.getId().toString(), HttpMethod.PUT, request, String.class);

        JSONAssert.assertEquals(mapper.writeValueAsString(member), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberDeleteById() {
        Member member = helper.createMember();

        ResponseEntity<String> response = restTemplate.exchange(
                "/member/" + member.getId().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
