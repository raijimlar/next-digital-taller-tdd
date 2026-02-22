package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinningGameTest {

    static Stream<Arguments> winProvider() {
        return Stream.of(
                // Victoria directa sin deuce
                Arguments.of(4, 0, "Player 1 wins"),
                Arguments.of(4, 1, "Player 1 wins"),
                Arguments.of(4, 2, "Player 1 wins"),
                Arguments.of(0, 4, "Player 2 wins"),
                Arguments.of(1, 4, "Player 2 wins"),
                Arguments.of(2, 4, "Player 2 wins"),

                // Victoria tras deuce (5-3 y 3-5 de la presentación)
                Arguments.of(5, 3, "Player 1 wins"),
                Arguments.of(3, 5, "Player 2 wins"),

                // Victoria tras múltiples deuces
                Arguments.of(6, 4, "Player 1 wins"),
                Arguments.of(4, 6, "Player 2 wins"),
                Arguments.of(12, 10, "Player 1 wins"),
                Arguments.of(10, 12, "Player 2 wins")
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → {2}")
    @MethodSource("winProvider")
    void should_return_win_when_player_leads_by_two_with_four_or_more(int p1Points, int p2Points, String expectedScore) {
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
