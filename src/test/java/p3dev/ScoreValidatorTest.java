package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreValidatorTest {

    static Stream<Arguments> validGameScoreProvider() {
        return Stream.of(
            // Normal scoring
            Arguments.of(0, 0),
            Arguments.of(1, 0),
            Arguments.of(0, 1),
            Arguments.of(2, 1),
            Arguments.of(3, 2),
            Arguments.of(3, 0),
            // Straight wins
            Arguments.of(4, 0),
            Arguments.of(4, 1),
            Arguments.of(4, 2),
            Arguments.of(0, 4),
            Arguments.of(1, 4),
            Arguments.of(2, 4),
            // Deuce zone
            Arguments.of(3, 3),
            Arguments.of(4, 3),
            Arguments.of(3, 4),
            Arguments.of(4, 4),
            Arguments.of(5, 4),
            Arguments.of(4, 5),
            // Deuce zone wins
            Arguments.of(5, 3),
            Arguments.of(3, 5),
            Arguments.of(6, 4),
            Arguments.of(4, 6),
            Arguments.of(10, 8),
            Arguments.of(8, 10)
        );
    }

    static Stream<Arguments> invalidGameScoreProvider() {
        return Stream.of(
            // Negative
            Arguments.of(-1, 0),
            Arguments.of(0, -1),
            Arguments.of(-3, -2),
            // Impossible: would have won earlier
            Arguments.of(5, 0),
            Arguments.of(5, 1),
            Arguments.of(5, 2),
            Arguments.of(0, 5),
            Arguments.of(1, 5),
            Arguments.of(2, 5),
            Arguments.of(6, 3),
            Arguments.of(3, 6),
            Arguments.of(7, 3),
            Arguments.of(3, 7),
            Arguments.of(10, 5),
            Arguments.of(5, 10),
            // Impossible gap in deuce zone
            Arguments.of(7, 4),
            Arguments.of(4, 7),
            Arguments.of(10, 7),
            Arguments.of(7, 10)
        );
    }

    @ParameterizedTest(name = "Valid game: {0}-{1}")
    @MethodSource("validGameScoreProvider")
    void should_accept_valid_game_score(int p1, int p2) {
        // Act & Assert
        assertDoesNotThrow(() -> ScoreValidator.validateGameScore(p1, p2));
    }

    @ParameterizedTest(name = "Invalid game: {0}-{1}")
    @MethodSource("invalidGameScoreProvider")
    void should_reject_invalid_game_score(int p1, int p2) {
        // Act & Assert
        assertThrows(InvalidScoreException.class, () -> ScoreValidator.validateGameScore(p1, p2));
    }

    // --- Match score validation ---

    static Stream<Arguments> validMatchScoreProvider() {
        return Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 0),
            Arguments.of(3, 2),
            Arguments.of(3, 3),
            Arguments.of(4, 0),
            Arguments.of(4, 2),
            Arguments.of(4, 3),
            Arguments.of(5, 3),
            Arguments.of(6, 4),
            Arguments.of(0, 4),
            Arguments.of(3, 5)
        );
    }

    static Stream<Arguments> invalidMatchScoreProvider() {
        return Stream.of(
            Arguments.of(-1, 0),
            Arguments.of(5, 0),
            Arguments.of(0, 5),
            Arguments.of(5, 2),
            Arguments.of(7, 3),
            Arguments.of(3, 7),
            Arguments.of(6, 3),
            Arguments.of(3, 6)
        );
    }

    @ParameterizedTest(name = "Valid match: {0}-{1}")
    @MethodSource("validMatchScoreProvider")
    void should_accept_valid_match_score(int p1, int p2) {
        assertDoesNotThrow(() -> ScoreValidator.validateMatchScore(p1, p2));
    }

    @ParameterizedTest(name = "Invalid match: {0}-{1}")
    @MethodSource("invalidMatchScoreProvider")
    void should_reject_invalid_match_score(int p1, int p2) {
        assertThrows(InvalidScoreException.class, () -> ScoreValidator.validateMatchScore(p1, p2));
    }
    static Stream<Arguments> validMatchStateProvider() {
        return Stream.of(
            Arguments.of(0, 0, 0, 0),
            Arguments.of(2, 1, 3, 2),
            Arguments.of(3, 3, 4, 3),
            Arguments.of(4, 2, 0, 0)
        );
    }

    static Stream<Arguments> invalidMatchStateProvider() {
        return Stream.of(
            Arguments.of(4, 2, 1, 0),   // match over, p1Points != 0
            Arguments.of(4, 2, 0, 1),   // match over, p1Points == 0 but p2Points != 0
            Arguments.of(7, 3, 0, 0),
            Arguments.of(2, 1, 7, 3),
            Arguments.of(2, 1, 4, 2)
        );
    }

    @ParameterizedTest(name = "Valid state: games={0}-{1} points={2}-{3}")
    @MethodSource("validMatchStateProvider")
    void should_accept_valid_match_state(int g1, int g2, int p1, int p2) {
        assertDoesNotThrow(() -> ScoreValidator.validateMatchState(g1, g2, p1, p2));
    }

    @ParameterizedTest(name = "Invalid state: games={0}-{1} points={2}-{3}")
    @MethodSource("invalidMatchStateProvider")
    void should_reject_invalid_match_state(int g1, int g2, int p1, int p2) {
        assertThrows(InvalidScoreException.class, () -> ScoreValidator.validateMatchState(g1, g2, p1, p2));
    }
}
