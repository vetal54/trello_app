package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.repository.CardRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CardService extends AbstractService<Card> {

    public CardService(CardRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public Card create() {
        Card card = new Card();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card name:");
        card.setName(scanner.nextLine());
        System.out.println("Enter the email of the user who created the card:");
        card.setCreateBy(scanner.nextLine());
        print(card);
        repository.create(card);
        return card;
    }

    @Override
    public void print(Card card) {
        System.out.println(card);
    }

    @Override
    public void update(Card card) {
        repository.update(card);
    }

    @Override
    public Optional<Card> findById(UUID id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new IllegalStateException("Card not found"));
    }

    @Override
    public List<Card> findAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
