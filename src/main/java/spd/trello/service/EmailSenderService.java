package spd.trello.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spd.trello.domian.Card;
import spd.trello.repository.CardRepository;

import java.util.List;

@Component
public class EmailSenderService implements EmailSender {

    private final CardRepository repository;

    public EmailSenderService(CardRepository repository) {
        this.repository = repository;
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
