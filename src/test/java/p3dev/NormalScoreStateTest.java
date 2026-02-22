package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.playerWithPoints;

public class NormalScoreStateTest {

    private final PointNameTranslator translator = new PointNameTranslator();
    private final NormalScoreState state = new NormalScoreState(translator);

    static Stream<Arguments> differentScoresProvider() {
        return Stream.of(
                Arguments.of(1, 0, "Fifteen-Love"),
                Arguments.of(0, 1, "Love-Fifteen"),
                Arguments.of(2, 0, "Thirty-Love"),
                Arguments.of(0, 2, "Love-Thirty"),
                Arguments.of(2, 1, "Thirty-Fifteen"),
                Arguments.of(1, 2, "Fifteen-Thirty"),
                Arguments.of(3, 0, "Forty-Love"),
                Arguments.of(0, 3, "Love-Forty"),
                Arguments.of(3, 1, "Forty-Fifteen"),
                Arguments.of(1, 3, "Fifteen-Forty"),
                Arguments.of(3, 2, "Forty-Thirty"),
                Arguments.of(2, 3, "Thirty-Forty")
        );
    }

    static Stream<Arguments> tiedScoresProvider() {
        return Stream.of(
                Arguments.of(0, "Love-All"),
                Arguments.of(1, "Fifteen-All"),
                Arguments.of(2, "Thirty-All")
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → \"{2}\"")
    @MethodSource("differentScoresProvider")
    void should_return_score_with_both_names_when_not_tied(int p1Points, int p2Points, String expected) {
        // Arrange
        Player p1 = playerWithPoints("Player 1", p1Points);
        Player p2 = playerWithPoints("Player 2", p2Points);

        // Act & Assert
        assertEquals(expected, state.getScore(p1, p2));
    }

    @ParameterizedTest(name = "Both={0} → \"{1}\"")
    @MethodSource("tiedScoresProvider")
    void should_return_all_suffix_when_tied(int points, String expected) {
        // Arrange
        Player p1 = playerWithPoints("Player 1", points);
        Player p2 = playerWithPoints("Player 2", points);

        // Act & Assert
        assertEquals(expected, state.getScore(p1, p2));
    }

    @Test
    void should_not_be_terminal() {
        assertFalse(state.isTerminal());
    }

    static Stream<Arguments> validNormalProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 0),
                Arguments.of(0, 1),
                Arguments.of(2, 1),
                Arguments.of(3, 0),
                Arguments.of(3, 2)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → true")
    @MethodSource("validNormalProvider")
    void should_apply_for_normal_scores(int p1, int p2) {
        assertTrue(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    static Stream<Arguments> invalidNormalProvider() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(4, 3),
                Arguments.of(4, 0),
                Arguments.of(5, 5)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → false")
    @MethodSource("invalidNormalProvider")
    void should_not_apply_for_non_normal_scores(int p1, int p2) {
        assertFalse(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @Test
    void should_throw_when_does_not_apply() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> state.getScore(playerWithPoints("P1", 3), playerWithPoints("P2", 3)));
    }
}
