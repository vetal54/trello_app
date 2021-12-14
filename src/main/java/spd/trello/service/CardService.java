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
        System.out.println("Enter information about the user who created the card");
        card.setCreateBy(new User());
        System.out.println("Enter first name:");
        card.getCreateBy().setFirstName(scanner.next());
        System.out.println("Enter last name:");
        card.getCreateBy().setLastName(scanner.next());
        System.out.println("Enter email:");
        card.getCreateBy().setEmail(scanner.next());
        return card;
    }

    @Override
    public void print(Card card) {
        System.out.println(card);
    }
}
