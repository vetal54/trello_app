package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepositoryImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest extends BaseTest {

    private Reminder reminder;
    private final ReminderService service;

    public ReminderServiceTest() {
        service = new ReminderService(new ReminderRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        reminder = new Reminder();
        reminder.setStart(LocalDateTime.now());
        reminder.setEnd(LocalDateTime.of(2022, 9, 19, 14, 5, 20));
        reminder.setRemindOn(LocalDateTime.of(2022, 9, 15, 10, 0, 0));
        reminder.setCardID(UUID.fromString("dd81005f-67fa-4060-af1f-56487389cccd"));
    }

    @Test
    void createComment() {
        assertNotNull(reminder);
        assertAll(
                () -> assertNotNull(reminder.getCreateDate()),
                () -> assertNull(reminder.getUpdateDate()),
                () -> assertTrue(reminder.getActive())
        );
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
        Optional<Reminder> byId = service.findById(reminder.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Reinder not found"
        );
        assertEquals("Reminder with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Reminder::findReminderById failed"
        );
        assertEquals("Reminder with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(reminder);
        reminder.setStart(LocalDateTime.of(2021, 9, 15, 10, 0, 0));
        service.update(reminder);
        Optional<Reminder> startReminder = service.findById(reminder.getId());
        assertEquals(LocalDateTime.of(2021, 9, 15, 10, 0, 0), startReminder.get().getStart());
    }

}