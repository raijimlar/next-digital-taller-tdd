package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicScoringTest {

    static Stream<Arguments> basicScoringProvider() {
        return Stream.of(
                // Cases de la presentación
                Arguments.of(0, 0, "Love-All"),
                Arguments.of(1, 0, "Fifteen-Love"),
                Arguments.of(0, 1, "Love-Fifteen"),
                Arguments.of(1, 1, "Fifteen-All"),
                Arguments.of(2, 0, "Thirty-Love"),
                Arguments.of(0, 2, "Love-Thirty"),
                Arguments.of(3, 0, "Forty-Love"),
                Arguments.of(0, 3, "Love-Forty"),

                // Cases adicionales
                Arguments.of(2, 1, "Thirty-Fifteen"),
                Arguments.of(1, 2, "Fifteen-Thirty"),
                Arguments.of(2, 2, "Thirty-All"),
                Arguments.of(3, 1, "Forty-Fifteen"),
                Arguments.of(1, 3, "Fifteen-Forty"),
                Arguments.of(3, 2, "Forty-Thirty"),
                Arguments.of(2, 3, "Thirty-Forty")
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → {2}")
    @MethodSource("basicScoringProvider")
    void should_return_correct_basic_score(int p1Points, int p2Points, String expectedScore) {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        scorePoints(game, p1Points, p2Points);

        // Act
        String result = game.getScore();

        // Assert
        assertEquals(expectedScore, result);
    }

    private void scorePoints(TennisGame game, int p1, int p2) {
        for (int i = 0; i < p1; i++) game.playerOneScores();
        for (int i = 0; i < p2; i++) game.playerTwoScores();
    }
}
