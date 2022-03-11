package blackjack.domain.player;

import java.util.Objects;

public class Name {

    private final String value;

    public Name(String value) {
        validateBlank(value);

        this.value = value;
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름은 공백 또는 빈 값이면 안됩니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Name name = (Name)o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
