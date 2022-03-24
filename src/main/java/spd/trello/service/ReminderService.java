package spd.trello.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spd.trello.domian.Card;
import spd.trello.domian.Reminder;
import spd.trello.exeption.ReminderTimeNotValidity;
import spd.trello.repository.CardRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderValidator reminderValidator;
    private final CardRepository repository;

    public ReminderService(ReminderValidator reminderValidator, CardRepository repository) {
        this.reminderValidator = reminderValidator;
        this.repository = repository;
    }

    public Reminder create(Timestamp start, Timestamp end, Timestamp remind) {
        Reminder reminder = new Reminder();
        reminder.setStart(start);
        reminder.setEnd(end);
        reminder.setRemindOn(remind);
        return reminder;
    }

    public void validate(Reminder reminder) {
        if (Boolean.FALSE.equals(reminderValidator.timeSequence(reminder)) ||
                Boolean.FALSE.equals(reminderValidator.timeValidity(reminder))) {
            throw new ReminderTimeNotValidity();
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendEmail() {
        List<Card> cards = repository.findAllCardByReminderDate();
        for(Card card :cards) {
            System.out.println("Hello to: " + card.getCreateBy() + ". Reminder time: " + card.getReminder().getRemindOn());
        }
    }
}
