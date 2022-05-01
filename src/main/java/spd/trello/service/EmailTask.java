package spd.trello.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import spd.trello.domian.Card;
import spd.trello.repository.CardRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@Setter
public class EmailTask implements Runnable {

    private final JavaMailSender mailSender;
    private final CardRepository cardRepository;
    private final String from;
    private Card card;
    private static AtomicInteger count = new AtomicInteger();

    public EmailTask(JavaMailSender mailSender, CardRepository cardRepository, String from) {
        this.mailSender = mailSender;
        this.cardRepository = cardRepository;
        this.from = from;
    }

    public Integer getCount() {
        return count.get();
    }

    @Override
    public void run() {
        try {
            log.info("Send email to: " + card.getCreateBy() + " at: " + card.getReminder().getRemindOn().toString());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(from);
            helper.setTo(card.getCreateBy());
            helper.setSubject("Reminder");
            mimeMessage.setContent("Remind you of the task. Time at: " + card.getReminder().getRemindOn(), "text/html");
            mailSender.send(mimeMessage);

            card.getReminder().setActive(false);
            cardRepository.save(card);

            count.incrementAndGet();
        } catch (MessagingException e) {
            log.error("Email send error: {}", e.getMessage());
        }
    }
}
