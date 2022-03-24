package spd.trello.service;

import org.springframework.stereotype.Component;
import spd.trello.domian.Reminder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ReminderValidator {

    public Boolean timeSequence(Reminder reminder) {
        return !reminder.getStart().after(reminder.getRemindOn()) &&
                !reminder.getRemindOn().after(reminder.getEnd()) &&
                !reminder.getStart().after(reminder.getEnd());
    }

    public Boolean timeValidity(Reminder reminder) {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        return !reminder.getStart().before(time) &&
                !reminder.getRemindOn().before(time) &&
                !reminder.getEnd().before(time);
    }
}
