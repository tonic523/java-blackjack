package blackjack.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardFactory;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardPattern;

public class CardTest {

    @Nested
    @DisplayName("of는")
    class Of {

        @Test
        @DisplayName("캐싱된 카드를 반환한다.")
        void returnCachedCard() {
            Card card1 = CardFactory.of(CardPattern.DIAMOND, CardNumber.JACK);
            Card card2 = CardFactory.of(CardPattern.DIAMOND, CardNumber.JACK);
            assertThat(card1).isSameAs(card2);
        }
    }

    @Nested
    @DisplayName("getNumber는")
    class GetNumber {

        @ParameterizedTest()
        @CsvSource(value = {"ACE|1", "TWO|2", "TEN|10", "QUEEN|10", "JACK|10", "KING|10"}, delimiter = '|')
        @DisplayName("Number를 반환한다.")
        void returnNumber(CardNumber cardNumber, int expected) {
            Card card = CardFactory.of(CardPattern.DIAMOND, cardNumber);
            assertThat(card.getNumberValue()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("getPattern는")
    class GetPattern {

        @ParameterizedTest
        @CsvSource(value = {"DIAMOND|다이아몬드", "CLOVER|클로버", "SPADE|스페이드", "HEART|하트"}, delimiter = '|')
        @DisplayName("Pattern을 반환한다.")
        void returnPattern(CardPattern cardPattern, String expected) {
            Card card = CardFactory.of(cardPattern, CardNumber.ACE);
            assertThat(card.getPatternName()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("isAce는")
    class IsAce {

        @ParameterizedTest
        @CsvSource(value = {"ACE|true", "TWO|false"}, delimiter = '|')
        @DisplayName("ACE인지 여부를 알려준다.")
        void returnBoolean(CardNumber cardNumber, boolean expected) {
            Card card = CardFactory.of(CardPattern.DIAMOND, cardNumber);

            assertThat(card.isAce()).isEqualTo(expected);
        }
    }
}
