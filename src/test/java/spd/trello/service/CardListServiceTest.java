package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import spd.trello.domian.CardList;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.CardListRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardListServiceTest {

    @Mock
    private CardListRepository repository;
    private CardList cardList;
    private CardListService service;

    void create() {
        cardList = new CardList();
        cardList.setName("vitaliy");
        cardList.setCreateBy("@gmail");
        cardList.setArchived(false);
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new CardListService(repository);
        create();
    }

    @Test
    void cardListSave() {
        when(repository.save(Mockito.any(CardList.class))).thenReturn(cardList);
        CardList savedCardList = repository.save(cardList);
        assertThat(savedCardList).isEqualTo(cardList);
    }

    @Test
    void emptyListOfCardLists() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<CardList> cardLists = service.findAll();
        assertThat(cardLists).isEmpty();
    }

    @Test
    void oneElementOfListCardLists() {
        when(repository.findAll()).thenReturn(
                List.of(
                        cardList
                )
        );
        List<CardList> cardLists = service.findAll();
        assertThat(cardLists).isEqualTo(List.of(cardList));
    }

    @Test
    void cardListWasNotFoundById() {
        when(repository.findById(cardList.getId()))
                .thenReturn(Optional.empty());

        assertThatCode(() -> service.findById(cardList.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardListWasFoundById() {
        when(repository.findById(cardList.getId())).thenReturn(
                Optional.of(cardList)
        );
        CardList cardListFindById = service.findById(cardList.getId());
        assertThat(cardListFindById).isEqualTo(cardList);
    }

    @Test
    void cardListWasDeleted() {
        service.delete(cardList.getId());
        verify(repository).deleteById(cardList.getId());
    }

    @Test
    void cardListWasUpdated() {
        when(repository.save(Mockito.any(CardList.class)))
                .thenReturn(cardList);
        cardList.setName("new Name");
        CardList updatedCardList = service.save(cardList);
        assertThat(updatedCardList.getName()).isEqualTo(cardList.getName());
    }
}