package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import spd.trello.domian.Card;
import spd.trello.domian.CheckList;
import spd.trello.domian.Reminder;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.CardRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository repository;
    private Card card;
    private CardService service;

    void create() {
        card = new Card();
        card.setName("vitaliy");
        card.setCreateBy("@gmail");
        card.setArchived(false);
        card.setReminder(new Reminder());
        card.setCheckList(new CheckList());
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new CardService(repository);
        create();
    }

    @Test
    void cardSave() {
        when(repository.save(Mockito.any(Card.class)))
                .thenReturn(card);
        Card savedCard = repository.save(card);
        assertThat(savedCard).isEqualTo(card);
    }

    @Test
    void emptyListOfCards() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Card> cards = service.findAll();
        assertThat(cards).isEmpty();
    }

    @Test
    void oneElementOfListCards() {
        when(repository.findAll()).thenReturn(
                List.of(
                        card
                )
        );
        List<Card> cards = service.findAll();
        assertThat(cards).isEqualTo(List.of(card));
    }

    @Test
    void cardWasNotFoundById() {
        when(repository.findById(card.getId()))
                .thenReturn(Optional.empty());

        assertThatCode(() -> service.findById(card.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardWasFoundById() {
        when(repository.findById(card.getId())).thenReturn(
                Optional.of(card)
        );
        Card cardFindById = service.findById(card.getId());
        assertThat(cardFindById).isEqualTo(card);
    }

    @Test
    void cardWasDeleted() {
        service.delete(card.getId());
        verify(repository).deleteById(card.getId());
    }

    @Test
    void cardWasUpdated() {
        when(repository.save(Mockito.any(Card.class)))
                .thenReturn(card);
        card.setName("new Name");
        Card updatedCard = service.save(card);
        assertThat(updatedCard.getName()).isEqualTo(card.getName());
    }
}