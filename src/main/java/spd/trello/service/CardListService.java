package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepositoryImpl;

import java.util.UUID;

@Service
public class CardListService extends ServiceLayer<CardList> {

    public CardListService(CardListRepositoryImpl repository) {
        super(repository);
    }

    public CardList create(String name, String email, UUID id) {
        CardList cardList = new CardList();
        cardList.setName(name);
        cardList.setCreateBy(email);
        cardList.setBoardId(id);
        print(cardList);
        repository.create(cardList);
        return repository.getById(cardList.getId());
    }
}
