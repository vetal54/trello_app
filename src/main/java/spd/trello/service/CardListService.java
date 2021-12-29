package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CardListService extends AbstractService<CardList> {

    public CardListService(CardListRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public CardList create() {
        CardList cardList = new CardList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card list name");
        cardList.setName(scanner.nextLine());
        print(cardList);
        repository.create(cardList);
        return cardList;
    }

    @Override
    public void print(CardList cardList) {
        System.out.println(cardList);
    }

    @Override
    void update(CardList cardList) {
        repository.update(cardList);
    }

    @Override
    Optional<CardList> findById(UUID id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new IllegalStateException("CardList not found"));
    }

    @Override
    List<CardList> findAll() {
        return repository.getAll();
    }

    @Override
    boolean delete(UUID id) {
        return repository.delete(id);
    }
}
