package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Item;
import spd.trello.domian.Reminder;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.exeption.UpdateImpossible;
import spd.trello.repository.CardListRepository;
import spd.trello.repository.CardRepository;
import spd.trello.repository.LabelRepository;
import spd.trello.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CardService extends AbstractResourceService<Card, CardRepository> implements Validator<Card> {

    private final CardListRepository cardListRepository;
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;

    public CardService(CardRepository repository, CardListRepository cardListRepository, LabelRepository labelRepository, MemberRepository memberRepository) {
        this.cardListRepository = cardListRepository;
        this.labelRepository = labelRepository;
        this.memberRepository = memberRepository;
        this.repository = repository;
    }

    @Override
    public Card save(Card card) throws JsonProcessingException {
        log.info("Try saving card");
        validateReference(card);
        helper(card);
        log.info("Card created successfully {}", mapper.writeValueAsString(card));
        return repository.save(card);
    }

    @Override
    public Card update(Card card) throws JsonProcessingException {
        log.info("Try updating card");
        if (Boolean.TRUE.equals(card.getArchived())) {
            log.error("Card updated impossible");
            throw new UpdateImpossible();
        }
        helper(card);
        validateReference(card);
        log.info("Card updated successfully {}", mapper.writeValueAsString(card));
        return repository.save(card);
    }

    private void helper(Card card) {
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
    }

    @Override
    public void validateReference(Card card) {
        log.info("Try checked foreign key");
        cardListRepository.findById(card.getCardListId()).orElseThrow(() ->
                new ResourceNotFoundException("CardList reference not valid. Id not corrected: " + card.getCardListId()));
        for (UUID id : card.getMemberIds()) {
            memberRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Member reference not valid. Id not corrected: " + id));
        }
        for (UUID id : card.getLabelIds()) {
            labelRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Label reference not valid. Id not corrected: " + id));
        }
    }
}

