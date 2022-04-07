package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Label;
import spd.trello.repository.LabelRepository;

import java.util.UUID;

@Service
public class LabelService extends AbstractDomainService<Label, LabelRepository> {

    public LabelService(LabelRepository repository) {
        super(repository);
    }

    public Label create(String name, String color, UUID id) {
        Label label = new Label();
        label.setName(name);
        label.setColor(color);
        label.setCardId(id);
        return repository.save(label);
    }
}
