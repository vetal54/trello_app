package spd.trello.repository;

import spd.trello.domian.AttachmentFile;

import java.util.UUID;

public interface AttachmentFileRepository extends AbstractRepository<AttachmentFile> {

    AttachmentFile findAttachmentFileByAttachmentId(UUID id);
}
