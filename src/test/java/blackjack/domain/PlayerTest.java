package blackjack.domain;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import blackjack.MockDeck;
import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardPattern;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Name;
import blackjack.domain.player.Player;

public class PlayerTest {

    @Nested
    @DisplayName("drawCard는")
    class DrawCard {

        @Test
        @DisplayName("Card를 자신의 패에 추가한다.")
        void addCard() {
            Player player = new Player(new Name("roma"));
            player.drawCard(new MockDeck(List.of(Card.of(CardPattern.CLOVER, CardNumber.JACK))));
            Assertions.assertThat(player.getTotalNumber()).isEqualTo(10);
        }
    }

    @Nested
    @DisplayName("isBust는")
    class IsBust {

        @ParameterizedTest
        @CsvSource(value = {"ACE|false", "TWO|true"}, delimiter = '|')
        @DisplayName("패의 합이 21이 넘는지 유무를 알려준다.")
        void returnFalse(CardNumber cardNumber, boolean expected) {
            Player player = new Player(new Name("roma"));
            MockDeck mockDeck = new MockDeck(List.of(Card.of(CardPattern.CLOVER, CardNumber.JACK)
                , Card.of(CardPattern.CLOVER, CardNumber.KING),
                Card.of(CardPattern.CLOVER, cardNumber)));
            player.drawCard(mockDeck);
            player.drawCard(mockDeck);
            player.drawCard(mockDeck);

            Assertions.assertThat(player.isBust()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Compete는")
    class Compete {

        @ParameterizedTest
        @CsvSource(value = {"FOUR|LOSE", "FIVE|DRAW", "SIX|WIN"}, delimiter = '|')
        @DisplayName("딜러와 점수를 비교하여 승부 결과를 반환한다.")
        void returnFalse(CardNumber cardNumber, Score expected) {
            Player player = new Player("player");
            Dealer dealer = new Dealer();
            MockDeck mockDeck = new MockDeck(List.of(Card.of(CardPattern.DIAMOND, cardNumber),
                Card.of(CardPattern.DIAMOND, CardNumber.FIVE)));
            player.drawCard(mockDeck);
            dealer.drawCard(mockDeck);
            Assertions.assertThat(player.compete(dealer)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(value = {"TEN|DRAW", "ACE|WIN"}, delimiter = '|')
        @DisplayName("딜러가 버스트일 때 승부 결과를 반환한다.")
        void returnResult(CardNumber cardNumber, Score expected) {
            Player player = new Player("player");
            Dealer dealer = new Dealer();
            player.drawCard(() -> Card.of(CardPattern.DIAMOND, CardNumber.TEN));
            player.drawCard(() -> Card.of(CardPattern.DIAMOND, CardNumber.TEN));
            player.drawCard(() -> Card.of(CardPattern.DIAMOND, cardNumber));

            dealer.drawCard(() -> Card.of(CardPattern.DIAMOND, CardNumber.TEN));
            dealer.drawCard(() -> Card.of(CardPattern.DIAMOND, CardNumber.TEN));
            dealer.drawCard(() -> Card.of(CardPattern.DIAMOND, CardNumber.TWO));

            Assertions.assertThat(player.compete(dealer)).isEqualTo(expected);
        }
    }
}
