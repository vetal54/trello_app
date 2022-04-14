package spd.trello.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spd.trello.domian.Card;
import spd.trello.domian.Reminder;
import spd.trello.repository.CardRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService implements EmailSender {

    private final CardRepository repository;

    public ReminderService(CardRepository repository) {
        this.repository = repository;
    }

    public Reminder create(LocalDateTime start, LocalDateTime end, LocalDateTime remind) {
        Reminder reminder = new Reminder();
        reminder.setStart(start);
        reminder.setEnd(end);
        reminder.setRemindOn(remind);
        return reminder;
    }

    @Scheduled(cron = "0 * * * * ?")
    @Override
    public void sendEmail() {
        List<Card> cards = repository.findAllCardByReminderDate();
        for (Card card : cards) {
            card.getReminder().setActive(false);
            repository.save(card);
            System.out.println("Hello to: " + card.getCreateBy() + ". Reminder time: " + card.getReminder().getRemindOn());
        }
    }
}
