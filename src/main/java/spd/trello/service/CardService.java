package spd.trello.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Item;
import spd.trello.domian.Reminder;
import spd.trello.exeption.UpdateImpossible;
import spd.trello.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CardService extends AbstractResourceService<Card, CardRepository> {

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card save(Card card) {
        log.info("Card was saved successfully");
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
            log.error("Card updated impossible");
            throw new UpdateImpossible();
        }
        log.info("Card was updated successfully");
        return repository.save(card);
    }
}

