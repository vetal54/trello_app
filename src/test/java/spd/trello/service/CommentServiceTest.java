package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spd.trello.domain.Comment;
import spd.trello.repository.CommentRepositoryImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest extends BaseTest {

    private Comment comment;
    private final CommentService service;

    public CommentServiceTest() {
        service = new CommentService(new CommentRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        comment = new Comment();
        comment.setText("comment");
        comment.setCardID(UUID.fromString("dd81005f-67fa-4060-af1f-56487389cccd"));
    }

    @Test
    void createComment() {
        assertNotNull(comment);
        assertAll(
                () -> assertNotNull(comment.getCreateDate()),
                () -> assertNull(comment.getUpdateDate()),
                () -> assertEquals("comment", comment.getText())
        );
    }

    @Test
    void printComment() {
        assertEquals(comment.getText() + ", date: " +  comment.getDate(), comment.toString());
    }

    @Test
    void testSave() {
        service.repository.create(comment);
        Optional<Comment> byId = service.findById(comment.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Comment not found"
        );
        assertEquals("Comment with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Comment::findCommentById failed"
        );
        assertEquals("Comment with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(comment);
        comment.setText("it`s update comment");
        service.update(comment);
        Optional<Comment> startComment = service.findById(comment.getId());
        assertEquals("it`s update comment", startComment.get().getText());
    }
}