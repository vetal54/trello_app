package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Label;
import spd.trello.repository.LabelRepository;

@Service
public class LabelService extends AbstractDomainService<Label, LabelRepository> {

    public LabelService(LabelRepository repository) {
        super(repository);
    }
}
