package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.playerWithPoints;

public class DeuceScoreStateTest {

    private final DeuceScoreState state = new DeuceScoreState();

    @Test
    void should_return_deuce_regardless_of_player_names() {
        // Arrange
        Player p1 = playerWithPoints("Nadal", 3);
        Player p2 = playerWithPoints("Federer", 3);

        // Act & Assert
        assertEquals("Deuce", state.getScore(p1, p2));
    }

    @Test
    void should_not_be_terminal() {
        assertFalse(state.isTerminal());
    }

    static Stream<Arguments> validDeuceProvider() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(4, 4),
                Arguments.of(5, 5),
                Arguments.of(10, 10)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → true")
    @MethodSource("validDeuceProvider")
    void should_apply_for_valid_deuce_scores(int p1, int p2) {
        assertTrue(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    static Stream<Arguments> invalidDeuceProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(2, 2),
                Arguments.of(4, 3),
                Arguments.of(3, 4),
                Arguments.of(5, 3)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → false")
    @MethodSource("invalidDeuceProvider")
    void should_not_apply_for_non_deuce_scores(int p1, int p2) {
        assertFalse(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @Test
    void should_throw_when_does_not_apply() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> state.getScore(playerWithPoints("P1", 2), playerWithPoints("P2", 2)));
    }
}
