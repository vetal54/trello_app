package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Attachment;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM attachment")
class AttachmentServiceTest {

    @Autowired
    private AttachmentService service;
    @Autowired
    private Helper helper;

    @Test
    void attachmentWasSaved() {
        Attachment attachment = helper.createAttachment();
        Attachment attachmentSave = service.findById(attachment.getId());
        assertThat(attachmentSave).isEqualTo(attachment);
    }

    @Test
    void emptyListOfAttachmentsIsReturned() {
        List<Attachment> attachments = service.findAll();

        assertThat(attachments).isEmpty();
    }

    @Test
    void notEmptyListOfAttachmentsIsReturned() {
        Attachment attachment = helper.createAttachment();

        List<Attachment> attachments = service.findAll();

        assertThat(attachments).isNotEmpty();
    }

    @Test
    void attachmentWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void attachmentWasFoundById() {
        Attachment attachment = helper.createAttachment();

        Attachment boardFindById = service.findById(attachment.getId());

        assertThat(boardFindById).isEqualTo(attachment);
    }

    @Test
    void attachmentWasDeleted() {
        Attachment attachment = helper.createAttachment();

        service.delete(attachment.getId());

        assertThatCode(() -> service.findById(attachment.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void attachmentWasUpdated() {
        Attachment savedAttachment = helper.createAttachment();
        savedAttachment.setName("new Name");

        Attachment updatedBoard = service.update(savedAttachment);

        assertThat(updatedBoard.getName()).isEqualTo("new Name");
    }
}
