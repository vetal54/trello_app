package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepositoryImpl;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReminderService extends ServiceLayer<Reminder> {

    public ReminderService(ReminderRepositoryImpl repository) {
        super(repository);
    }

    public Reminder create(LocalDateTime start, LocalDateTime end, LocalDateTime remind, UUID id) {
        Reminder reminder = new Reminder();
        reminder.setStart(start);
        reminder.setEnd(end);
        reminder.setRemindOn(remind);
        reminder.setCardId(id);
        print(reminder);
        repository.create(reminder);
        return repository.getById(reminder.getId());
    }
}
