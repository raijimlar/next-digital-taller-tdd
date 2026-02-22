package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeuceAndAdvantageTest {

    static Stream<Arguments> deuceProvider() {
        return Stream.of(
                Arguments.of(3, 3, "Deuce"),
                Arguments.of(4, 4, "Deuce"),
                Arguments.of(5, 5, "Deuce"),
                Arguments.of(10, 10, "Deuce")
        );
    }

    static Stream<Arguments> advantageProvider() {
        return Stream.of(
                Arguments.of(4, 3, "Advantage Player 1"),
                Arguments.of(3, 4, "Advantage Player 2"),
                Arguments.of(5, 4, "Advantage Player 1"),
                Arguments.of(4, 5, "Advantage Player 2"),
                Arguments.of(11, 10, "Advantage Player 1")
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → {2}")
    @MethodSource("deuceProvider")
    void should_return_deuce_when_tied_at_forty_or_above(int p1Points, int p2Points, String expectedScore) {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        scoreAlternating(game, p1Points, p2Points);

        // Act
        String result = game.getScore();

        // Assert
        assertEquals(expectedScore, result);
    }

    @ParameterizedTest(name = "P1={0} P2={1} → {2}")
    @MethodSource("advantageProvider")
    void should_return_advantage_when_one_point_ahead_after_deuce(int p1Points, int p2Points, String expectedScore) {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        scoreAlternating(game, p1Points, p2Points);

        // Act
        String result = game.getScore();

        // Assert
        assertEquals(expectedScore, result);
    }

    private void scoreAlternating(TennisGame game, int p1, int p2) {
        int min = Math.min(p1, p2);
        for (int i = 0; i < min; i++) {
            game.playerOneScores();
            game.playerTwoScores();
        }
        for (int i = min; i < p1; i++) game.playerOneScores();
        for (int i = min; i < p2; i++) game.playerTwoScores();
    }
}
