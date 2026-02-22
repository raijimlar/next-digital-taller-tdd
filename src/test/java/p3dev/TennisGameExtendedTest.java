package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TennisGameExtendedTest {

    static Stream<Arguments> fromScoreProvider() {
        return Stream.of(
                Arguments.of(0, 0, "Love-All"),
                Arguments.of(1, 0, "Fifteen-Love"),
                Arguments.of(3, 2, "Forty-Thirty"),
                Arguments.of(3, 3, "Deuce"),
                Arguments.of(4, 3, "Advantage Player 1"),
                Arguments.of(3, 4, "Advantage Player 2"),
                Arguments.of(5, 3, "Player 1 wins"),
                Arguments.of(4, 0, "Player 1 wins"),
                Arguments.of(0, 4, "Player 2 wins")
        );
    }

    @ParameterizedTest(name = "fromScore({0},{1}) → {2}")
    @MethodSource("fromScoreProvider")
    void should_translate_existing_score(int p1, int p2, String expectedScore) {
        TennisGame game = TennisGame.fromScore("Player 1", "Player 2", p1, p2);
        assertEquals(expectedScore, game.getScore());
    }

    @Test
    void should_throw_on_impossible_score() {
        assertThrows(InvalidScoreException.class,
                () -> TennisGame.fromScore("P1", "P2", 7, 3));

        assertThrows(InvalidScoreException.class,
                () -> TennisGame.fromScore("P1", "P2", -1, 0));

        assertThrows(InvalidScoreException.class,
                () -> TennisGame.fromScore("P1", "P2", 5, 0));
    }

    @Test
    void should_not_be_over_at_start() {
        TennisGame game = new TennisGame("P1", "P2");
        assertFalse(game.isGameOver());
        assertNull(game.getGameWinner());
    }

    @Test
    void should_be_over_after_four_straight_points() {
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.playerOneScores();

        assertTrue(game.isGameOver());
        assertEquals("Player 1", game.getGameWinner());
    }

    @Test
    void should_not_be_over_at_deuce() {
        TennisGame game = TennisGame.fromScore("P1", "P2", 3, 3);
        assertFalse(game.isGameOver());
    }

    @Test
    void should_not_be_over_at_advantage() {
        TennisGame game = TennisGame.fromScore("P1", "P2", 4, 3);
        assertFalse(game.isGameOver());
    }

    @Test
    void should_throw_when_scoring_after_game_over() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.playerOneScores();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> game.playerOneScores());
        assertThrows(IllegalStateException.class, () -> game.playerTwoScores());
    }
}
