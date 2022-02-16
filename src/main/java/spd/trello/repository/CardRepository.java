package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domian.Card;

@Repository
public interface CardRepository extends AbstractRepository<Card> {
}
