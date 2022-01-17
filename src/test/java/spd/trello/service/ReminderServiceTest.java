package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
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

    private static Reminder reminder;
    private final ReminderService service;

    public ReminderServiceTest() {
        service = new ReminderService(new ReminderRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        Card card = new Card("CardName");
        card.setDescription("New year 2022");
        CardService cs = new CardService(new CardRepositoryImpl(dataSource));
        cs.repository.create(card);
        reminder = new Reminder();
        reminder.setStart(LocalDateTime.now());
        reminder.setEnd(LocalDateTime.of(2022, 9, 19, 14, 5, 20));
        reminder.setRemindOn(LocalDateTime.of(2022, 9, 15, 10, 0, 0));
        reminder.setCardId(card.getId());
    }

    @Test
    void printComment() {
        assertEquals(reminder
                .getRemindOn()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")), reminder.toString());
    }

    @Test
    void testSave() {
        service.repository.create(reminder);
        Reminder byId = service.findById(reminder.getId());
        assertEquals(reminder.getRemindOn(), byId.getRemindOn());
    }

    @Test
    void testFindById() {
        Reminder findReminder = service.findById(reminder.getId());
        assertEquals(reminder.getRemindOn(), findReminder.getRemindOn());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Reinder not found"
        );
        assertEquals("Reminder with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(reminder);
        reminder.setStart(LocalDateTime.of(2021, 9, 15, 10, 0, 0));
        service.update(reminder);
        Reminder startReminder = service.findById(reminder.getId());
        assertEquals(reminder.getStart(), startReminder.getStart());
    }

    @Test
    void testDelete() {
        boolean bool = service.delete(reminder.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Reminder::findReminderById failed"
        );
        assertEquals("Reminder with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}