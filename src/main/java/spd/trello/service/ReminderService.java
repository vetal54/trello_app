package spd.trello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spd.trello.domian.Card;
import spd.trello.repository.CardRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReminderService implements EmailSender {

    private final JavaMailSender mailSender;
    private final CardRepository repository;
    @Value("${spring.mail.username}")
    private String from;

    public ReminderService(JavaMailSender mailSender, CardRepository repository) {
        this.mailSender = mailSender;
        this.repository = repository;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Scheduled(cron = "0 * * * * ?")
    @Override
    public void sendEmail() throws InterruptedException {
        log.info("Find emails which need send");
        final ExecutorService executorService = Executors.newWorkStealingPool(5);
        EmailTask emailTask = new EmailTask(mailSender, repository, from);
        for (Card card : findAllCard()) {
            emailTask = new EmailTask(mailSender, repository, from);
            emailTask.setCard(card);
            executorService.submit(emailTask);
        }
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        executorService.shutdown();
        log.info("{} messages successfully sent in total", emailTask.getCount());
    }

    private List<Card> findAllCard() {
        return repository.findAllCardByReminderCurrentTime();
    }
}
