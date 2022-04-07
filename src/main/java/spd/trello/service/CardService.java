package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Item;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Reminder;
import spd.trello.exeption.UpdateArchivedCardImpossible;
import spd.trello.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CardService extends AbstractResourceService<Card, CardRepository> {

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public Card create(String name, String email, String description, Reminder reminder, CheckList checkList, UUID id) {
        Card card = new Card();
        card.setName(name);
        card.setCreateBy(email);
        card.setDescription(description);

        reminder.setCard(card);
        card.setReminder(reminder);

        checkList.setCard(card);
        card.setCheckList(checkList);

        List<Item> items = new ArrayList<>();
        for (Item item : checkList.getItems()) {
            items.add(item);
            item.setCheckList(checkList);
        }

        checkList.setItems(items);

        card.setCardListId(id);
        return repository.save(card);
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

    @Override
    public Card update(Card card) {
        if (Boolean.TRUE.equals(card.getArchived())) {
            throw new UpdateArchivedCardImpossible();
        }
        return repository.save(card);
    }
}

