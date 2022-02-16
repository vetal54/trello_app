package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Reminder;

import java.sql.Timestamp;

@Service
public class ReminderService {

    public Reminder create(Timestamp start, Timestamp end, Timestamp remind) {
        Reminder reminder = new Reminder();
        reminder.setStart(start);
        reminder.setEnd(end);
        reminder.setRemindOn(remind);
        return reminder;
    }
}
