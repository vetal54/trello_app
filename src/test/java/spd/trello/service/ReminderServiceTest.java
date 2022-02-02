package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Card;
import spd.trello.domain.Reminder;
import spd.trello.repository.CardRepositoryImpl;
import spd.trello.repository.ReminderRepositoryImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest extends BaseTest {

    private Reminder reminder;
    private final ReminderService service;

    public ReminderServiceTest() {
        service = new ReminderService(new ReminderRepositoryImpl(jdbcTemplate));
    }

    @BeforeEach
    void create() {
        Card card = new Card( );
        card.setDescription("New year 2022");
        CardService cs = new CardService(new CardRepositoryImpl(jdbcTemplate));
        cs.repository.save(card);
        reminder = new Reminder();
        reminder.setStart(LocalDateTime.now());
        reminder.setEnd(LocalDateTime.of(2022, 9, 19, 14, 5, 20));
        reminder.setRemindOn(LocalDateTime.of(2022, 9, 15, 10, 0, 0));
        reminder.setCardId(card.getId());
    }

    @Test
    void printReminder() {
        assertEquals("remind on: " + reminder
                .getRemindOn()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")), reminder.toString());
    }

    @Test
    void testSave() {
        service.repository.save(reminder);
        Reminder byId = service.findById(reminder.getId());
        assertEquals(reminder.getRemindOn(), byId.getRemindOn());
    }

    @Test
    void testFindById() {
        service.repository.save(reminder);
        Reminder findReminder = service.findById(reminder.getId());
        assertEquals(reminder.getRemindOn(), findReminder.getRemindOn());
    }

    @Test
    void testUpdate() {
        service.repository.save(reminder);
        reminder.setStart(LocalDateTime.of(2021, 9, 15, 10, 0, 0));
        service.update(reminder);
        Reminder startReminder = service.findById(reminder.getId());
        assertEquals(reminder.getStart(), startReminder.getStart());
    }

    @Test
    void testFindAll() {
        service.repository.save(reminder);
        service.create(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), reminder.getCardId());
        service.create(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), reminder.getCardId());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDelete() {
        service.repository.save(reminder);
        boolean bool = service.delete(reminder.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}