package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.*;

public class TennisGameTest {

    static Stream<Arguments> basicScoringProvider() {
        return Stream.of(
                Arguments.of(0, 0, "Love-All"),
                Arguments.of(1, 0, "Fifteen-Love"),
                Arguments.of(0, 1, "Love-Fifteen"),
                Arguments.of(1, 1, "Fifteen-All"),
                Arguments.of(2, 0, "Thirty-Love"),
                Arguments.of(0, 2, "Love-Thirty"),
                Arguments.of(3, 0, "Forty-Love"),
                Arguments.of(0, 3, "Love-Forty"),
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

        // Act & Assert
        assertEquals(expectedScore, game.getScore());
    }

    static Stream<Arguments> deuceProvider() {
        return Stream.of(
                Arguments.of(3, 3, "Deuce"),
                Arguments.of(4, 4, "Deuce"),
                Arguments.of(5, 5, "Deuce"),
                Arguments.of(10, 10, "Deuce")
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → {2}")
    @MethodSource("deuceProvider")
    void should_return_deuce_when_tied_at_forty_or_above(int p1Points, int p2Points, String expectedScore) {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        scoreAlternating(game, p1Points, p2Points);

        // Act & Assert
        assertEquals(expectedScore, game.getScore());
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
    @MethodSource("advantageProvider")
    void should_return_advantage_when_one_point_ahead_after_deuce(int p1Points, int p2Points, String expectedScore) {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        scoreAlternating(game, p1Points, p2Points);

        // Act & Assert
        assertEquals(expectedScore, game.getScore());
    }

    static Stream<Arguments> winProvider() {
        return Stream.of(
                Arguments.of(4, 0, "Player 1 wins"),
                Arguments.of(4, 1, "Player 1 wins"),
                Arguments.of(4, 2, "Player 1 wins"),
                Arguments.of(0, 4, "Player 2 wins"),
                Arguments.of(1, 4, "Player 2 wins"),
                Arguments.of(2, 4, "Player 2 wins"),
                Arguments.of(5, 3, "Player 1 wins"),
                Arguments.of(3, 5, "Player 2 wins"),
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

        // Act & Assert
        assertEquals(expectedScore, game.getScore());
    }

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
        // Act
        TennisGame game = TennisGame.fromScore("Player 1", "Player 2", p1, p2);

        // Assert
        assertEquals(expectedScore, game.getScore());
    }

    static Stream<Arguments> invalidFromScoreProvider() {
        return Stream.of(
                Arguments.of(7, 3),
                Arguments.of(-1, 0),
                Arguments.of(5, 0)
        );
    }

    @ParameterizedTest(name = "fromScore({0},{1}) → throws")
    @MethodSource("invalidFromScoreProvider")
    void should_throw_on_impossible_score(int p1, int p2) {
        // Act & Assert
        assertThrows(InvalidScoreException.class,
                () -> TennisGame.fromScore("P1", "P2", p1, p2));
    }

    @Test
    void should_not_be_over_at_start() {
        TennisGame game = new TennisGame("P1", "P2");
        assertFalse(game.isGameOver());
        assertNull(game.getGameWinner());
    }

    @Test
    void should_be_over_after_four_straight_points() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.playerOneScores();

        // Assert
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
    void should_throw_when_player_one_scores_after_game_over() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.playerOneScores();

        // Act & Assert
        assertThrows(IllegalStateException.class, game::playerOneScores);
    }

    @Test
    void should_throw_when_player_two_scores_after_game_over() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.playerOneScores();

        // Act & Assert
        assertThrows(IllegalStateException.class, game::playerTwoScores);
    }

    @Test
    void should_score_via_scorePoint_for_player_1() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");

        // Act
        game.scorePoint(1);

        // Assert
        assertEquals("Fifteen-Love", game.getScore());
    }

    @Test
    void should_score_via_scorePoint_for_player_2() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");

        // Act
        game.scorePoint(2);

        // Assert
        assertEquals("Love-Fifteen", game.getScore());
    }

    @Test
    void should_throw_for_invalid_player_number() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> game.scorePoint(3));
    }

    @Test
    void should_throw_scorePoint_after_game_over() {
        // Arrange
        TennisGame game = new TennisGame("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) game.scorePoint(1);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> game.scorePoint(1));
    }
}
