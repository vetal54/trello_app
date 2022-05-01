package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spd.trello.domian.Card;

import java.util.List;

@Repository
public interface CardRepository extends AbstractRepository<Card> {

    @Query(
            value = "SELECT * FROM Card c INNER JOIN Reminder r ON c.id = r.card_id and r.remind_on <= current_timestamp and r.active = true",
            nativeQuery = true
    )
    List<Card> findAllCardByReminderCurrentTime();
}
