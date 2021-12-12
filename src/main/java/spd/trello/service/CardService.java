package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.domain.User;

public class CardService extends AbstractService<Card> {

    @Override
    public Card create() {
        Card card = new Card();
        card.setName("card1");
        card.setCreateBy(new User());
        card.getCreateBy().setFirstName("Vlad");
        card.getCreateBy().setLastName("Petrenko");
        card.getCreateBy().setEmail("vitaliy.dubovyk.1@gmail.com");
        return card;
    }

    @Override
    public void print(Card card) {
        System.out.println(card);
    }
}
