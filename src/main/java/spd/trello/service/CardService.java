package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.domain.User;

import java.util.Scanner;

public class CardService extends AbstractService<Card> {

    @Override
    public Card create() {
        Card card = new Card();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card name:");
        card.setName(scanner.next());
        System.out.println("Enter the email of the user who created the card:");
        card.setCreateBy(scanner.next());
        return card;
    }

    @Override
    public void print(Card card) {
        System.out.println(card);
    }
}
