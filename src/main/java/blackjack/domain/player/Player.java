package blackjack.domain.player;

import static blackjack.domain.card.Cards.BLACKJACK_NUMBER;

import blackjack.domain.blackjackgame.Money;

public class Player extends Participant {

    private GameResult gameResult;

    public Player(String name) {
        super(name);
    }

    public GameResult getResult() {
        return gameResult;
    }

    public boolean canDraw() {
        return state.getScore() < BLACKJACK_NUMBER;
    }

    public void matchGameResult(Dealer dealer) {
        if (this.isBust() || dealer.isBust()) {
            calculateBustGameResult(dealer);
            return;
        }
        calculateScoreGameResult(dealer);
    }

    private void calculateBustGameResult(Dealer dealer) {
        if (this.isBust()) {
            decideGameResult(GameResult.LOSE, dealer);
            return;
        }
        decideGameResult(GameResult.WIN, dealer);
    }

    private void calculateScoreGameResult(Dealer dealer) {
        if (this.getScore() > dealer.getScore()) {
            decideGameResult(GameResult.WIN, dealer);
            return;
        }
        if (this.getScore() < dealer.getScore()) {
            decideGameResult(GameResult.LOSE, dealer);
            return;
        }
        decideGameResult(GameResult.DRAW, dealer);
    }

    private void decideGameResult(GameResult gameResult, Dealer dealer) {
        this.gameResult = gameResult;
        dealer.addGameResult(GameResult.findOppositeGameResult(gameResult));
    }

    public Money profit(Dealer dealer) {
        if (this.isBlackjack() && dealer.isBlackjack()) {
            return money.profit(gameResult.earningRate());
        }
        if (this.isBust() || this.isBlackjack()) {
            return money.profit(state.earningRate());
        }
        return money.profit(gameResult.earningRate());
    }

}


