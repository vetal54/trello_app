package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Label;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.CardRepository;
import spd.trello.repository.LabelRepository;

@Service
@Slf4j
public class LabelService extends AbstractDomainService<Label, LabelRepository> implements Validator<Label> {

    private final CardRepository cardRepository;

    public LabelService(LabelRepository repository, CardRepository cardRepository) {
        super(repository);
        this.cardRepository = cardRepository;
    }

    @Override
    public Label save(Label label) throws JsonProcessingException {
        log.info("Try saving label");
        validateReference(label);
        log.info("Label created successfully {}", mapper.writeValueAsString(label));
        return repository.save(label);
    }

    @Override
    public Label update(Label label) throws JsonProcessingException {
        log.info("Try updating label");
        validateReference(label);
        log.info("Label updated successfully {}", mapper.writeValueAsString(label));
        return repository.save(label);
    }

    @Override
    public void validateReference(Label label) {
        log.info("Try checked foreign key");
        cardRepository.findById(label.getCardId()).orElseThrow(() ->
                new ResourceNotFoundException("Card reference not valid. Id not corrected: " + label.getCardId()));
    }
}
