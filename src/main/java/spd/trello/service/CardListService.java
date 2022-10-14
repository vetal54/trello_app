package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.CardList;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.CardListRepository;
import spd.trello.repository.CardRepository;

import java.util.UUID;

@Service
@Slf4j
public class CardListService extends AbstractResourceService<CardList, CardListRepository> implements Validator<CardList> {

    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;


    public CardListService(CardListRepository repository, BoardRepository boardRepository, CardRepository cardRepository) {
        super(repository);
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public CardList save(CardList cardList) throws JsonProcessingException {
        log.info("Try saving cardList");
        validateReference(cardList);
        log.info("CardList created successfully {}", mapper.writeValueAsString(cardList));
        return repository.save(cardList);
    }

    @Override
    public CardList update(CardList cardList) throws JsonProcessingException {
        log.info("Try updating cardList");
        validateReference(cardList);
        log.info("CardList updated successfully {}", mapper.writeValueAsString(cardList));
        return repository.save(cardList);
    }

    @Override
    public void validateReference(CardList cardList) {
        log.info("Try checked foreign key");
        boardRepository.findById(cardList.getBoardId()).orElseThrow(() ->
                new ResourceNotFoundException("Board reference not valid. Id not corrected: " + cardList.getBoardId()));
        for (UUID id : cardList.getCards()) {
            cardRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Card reference not valid. Id not corrected: " + id));
        }
    }
}
