package blackjack.domain.player;

import java.util.List;

import blackjack.domain.card.Drawable;
import blackjack.domain.card.Cards;
import blackjack.domain.Score;
import blackjack.domain.card.Card;

public class Player {

    public static final int BLACKJACK_NUMBER = 21;

    private final Cards cards;
    private final Name name;

    public Player(String name) {
        this(new Name(name));
    }

    public Player(Name name) {
        this.cards = new Cards();
        this.name = name;
    }

    public void drawCard(Drawable drawable) {
        cards.add(drawable.draw());
    }

    public boolean isBust() {
        return getTotalNumber() > BLACKJACK_NUMBER;
    }

    public int getTotalNumber() {
        return cards.getTotalNumber();
    }

    public String getName() {
        return name.getValue();
    }

    public List<Card> getCards() {
        return cards.getCards();
    }

    public Score compete(Player otherPlayer) {
        if (!this.isBust() && !otherPlayer.isBust()) {
            return Score.compete(this.getTotalNumber(), otherPlayer.getTotalNumber());
        }
        if (this.isBust() && otherPlayer.isBust()) {
            return Score.DRAW;
        }
        if (otherPlayer.isBust()) {
            return Score.WIN;
        }
        return Score.LOSE;
    }
}
