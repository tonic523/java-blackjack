package com.blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HitDecideTypeTest {
	@DisplayName("of에서 y 또는 n을 받아 열거형 반환")
	@ParameterizedTest
	@ValueSource(strings = {"y", "n"})
	void of(String input) {
		assertThat(HitDecideType.of(input)).isInstanceOf(HitDecideType.class);
	}

	@DisplayName("of에서 열거형에 존재하지 않는 문자열을 받으면 예외 발생")
	@ParameterizedTest
	@ValueSource(strings = {"a", ""})
	void of_GivenInvalidInput_ExceptionThrown(String input) {
		assertThatThrownBy(() -> HitDecideType.of(input)).isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("of에서 y 또는 n을 받아 열거형 반환")
	@Test
	void isDraw() {
		assertThat(HitDecideType.HIT.isHit()).isTrue();
	}
}