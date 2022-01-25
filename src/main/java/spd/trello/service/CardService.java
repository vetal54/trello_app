package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Card;
import spd.trello.repository.CardRepositoryImpl;

import java.util.UUID;

@Service
public class CardService extends ServiceLayer<Card> {

    public CardService(CardRepositoryImpl repository) {
        super(repository);
    }

    public Card create(String name, String email, String description, UUID id) {
        Card card = new Card();
        card.setName(name);
        card.setCreateBy(email);
        card.setDescription(description);
        card.setCardListId(id);
        print(card);
        repository.create(card);
        return repository.getById(card.getId());
    }
}
