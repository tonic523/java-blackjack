package blackjack.domain.player;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import blackjack.MockDeck;
import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardPattern;

public class DealerTest {

    @Nested
    @DisplayName("isDrawable은")
    class IsDrawable {

        @ParameterizedTest
        @CsvSource(value = {"SIX|true", "SEVEN|false"}, delimiter = '|')
        @DisplayName("패의 합이 17이 넘는지 유무를 알려준다.")
        void returnFalse(CardNumber cardNumber, boolean expected) {
            MockDeck mockDeck = new MockDeck(
                List.of(Card.of(CardPattern.CLOVER, CardNumber.TEN),
                    Card.of(CardPattern.CLOVER, cardNumber)));
            Dealer dealer = new Dealer(mockDeck);

            Assertions.assertThat(dealer.isDrawable()).isEqualTo(expected);
        }
    }
}
