package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.entity.resource.Card;

@Repository
public interface CardRepositoryImpl extends AbstractRepository<Card> {
}
