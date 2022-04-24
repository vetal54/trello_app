package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Member;
import spd.trello.domian.User;
import spd.trello.domian.type.Role;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM member")
@Sql(statements = "DELETE FROM user_table")
class MemberControllerIntegrationTest extends AbstractIntegrationTest<Member> {

    private final String URL_TEMPLATE = "/member";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        User user = helper.createUser();
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setUserId(user.getId());

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, member, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(member.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(member.getRole().toString(), getValue(mvc, "$.role"))
        );
    }

    @Test
    void delete() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, member.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, member.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(member.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(member.getRole().toString(), getValue(mvc, "$.role"))
        );
    }

    @Test
    void findByIdFailed() throws Exception {
        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, UUID.randomUUID(), token);

        assertEquals(HttpStatus.NOT_FOUND.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findAll() throws Exception {
        User user = helper.createUser();
        Member firstMember = helper.createMember(user);
        Member secondMember = helper.createMember(user);

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Member> members = helper.getMembersArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(members.contains(firstMember)),
                () -> assertTrue(members.contains(secondMember))
        );
    }

    @Test
    void update() throws Exception {
        User user = helper.createUser();
        Member member = helper.createMember(user);
        member.setRole(Role.GUEST);

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, member.getId(), member, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(member.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(Role.GUEST.toString(), getValue(mvc, "$.role"))
        );
    }
}
