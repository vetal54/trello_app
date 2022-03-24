package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spd.trello.domian.Card;

import java.util.List;

@Repository
public interface CardRepository extends AbstractRepository<Card> {

    @Query(
            value = "SELECT * FROM Card c INNER JOIN Reminder r ON c.id = r.card_id and cast (r.remind_on as date) = CURRENT_DATE",
            nativeQuery = true
    )
    List<Card> findAllCardByReminderDate();
}
