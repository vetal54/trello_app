package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Reminder;
import spd.trello.domian.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM card")
@Sql(statements = "DELETE FROM user_table")
class CardControllerIntegrationTest extends AbstractIntegrationTest<Card> {

    private final String URL_TEMPLATE = "/card";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        Card card = new Card();
        card.setName("string name");
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

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, card, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(card.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(card.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(card.getDescription(), getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        Card card = new Card();
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

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, card, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Name should not be empty", getValue(mvc, "$.details.name"))
        );
    }

    @Test
    void delete() throws Exception {
        Card card = helper.createCard();
        User user = helper.createUser();

        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.delete(URL_TEMPLATE, card.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        Card card = helper.createCard();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, card.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(card.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(card.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(card.getDescription(), getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
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
        Card firstCard = helper.createCard();
        Card secondCard = helper.createCard();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Card> cards = helper.getCardsArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(cards.contains(firstCard)),
                () -> assertTrue(cards.contains(secondCard))
        );
    }

    @Test
    void update() throws Exception {
        Card card = helper.createCard();
        card.setName("new Name");
        card.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, card.getId(), card, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(card.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.name")),
                () -> assertEquals("new Description", getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        Card card = helper.createCard();
        card.setName("n");
        card.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, card.getId(), card, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 30", getValue(mvc, "$.details.name"))
        );
    }
}
