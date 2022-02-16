package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Item;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Reminder;
import spd.trello.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CardService extends AbstractService<Card, CardRepository> {

    public CardService(CardRepository repository) {
        super(repository);
    }

    @Override
    public Card save(Card card) {
        Reminder reminder = card.getReminder();
        reminder.setCard(card);

        CheckList checkList = card.getCheckList();
        checkList.setCard(card);

        List<Item> items = new ArrayList<>();
        for (Item item : card.getCheckList().getItems()) {
            items.add(item);
            item.setCheckList(checkList);
        }

        checkList.setItems(items);

        return repository.save(card);
    }

    public Card create(String name, String email, String description, UUID id) {
        Card card = new Card();
        card.setName(name);
        card.setCreateBy(email);
        card.setDescription(description);
        card.setCardListId(id);
        repository.save(card);
        return repository.getById(card.getId());
    }
}
