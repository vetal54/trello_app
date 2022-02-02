package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Card;
import spd.trello.domain.Comment;
import spd.trello.repository.CardRepositoryImpl;
import spd.trello.repository.CommentRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest extends BaseTest {

    private Comment comment;
    private final CommentService service;

    public CommentServiceTest() {
        service = new CommentService(new CommentRepositoryImpl(jdbcTemplate));
    }

    @BeforeEach
    void create() {
        Card card = new Card( );
        card.setDescription("New year 2022");
        CardService cs = new CardService(new CardRepositoryImpl(jdbcTemplate));
        cs.repository.save(card);
        comment = new Comment();
        comment.setText("comment");
        comment.setCardId(card.getId());
    }

    @Test
    void printComment() {
        assertEquals(comment.getText() + ", date: " + comment.getDate(), comment.toString());
    }

    @Test
    void testSave() {
        service.repository.save(comment);
        Comment byId = service.findById(comment.getId());
        assertEquals(comment.getText(), byId.getText());
    }

    @Test
    void testFindById() {
        service.repository.save(comment);
        Comment findComment = service.findById(comment.getId());
        assertEquals(comment.getText(), findComment.getText());
    }

    @Test
    void testUpdate() {
        service.repository.save(comment);
        comment.setText("it`s update comment");
        service.update(comment);
        Comment startComment = service.findById(comment.getId());
        assertEquals(comment.getText(), startComment.getText());
    }

    @Test
    void testFindAll() {
        service.repository.save(comment);
        service.create("text", "v@gmail.com", comment.getCardId());
        service.create("text2", "d@gmail.com", comment.getCardId());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDelete() {
        service.repository.save(comment);
        boolean bool = service.delete(comment.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}