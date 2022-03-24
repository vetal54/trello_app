package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.*;
import spd.trello.repository.AttachmentRepository;


@Service
public class AttachmentService extends AbstractResourceService<Attachment, AttachmentRepository> {

    public AttachmentService(AttachmentRepository repository) {
        super(repository);
    }
}
