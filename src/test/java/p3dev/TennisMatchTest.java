package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.*;

public class TennisMatchTest {

    @Test
    void should_start_with_zero_games() {
        // Arrange & Act
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Assert
        assertEquals(0, match.getPlayer1Games());
        assertEquals(0, match.getPlayer2Games());
        assertEquals("Games: 0-0", match.getMatchScore());
    }

    @Test
    void should_increment_games_when_player_one_wins_a_game() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act
        winGameForPlayerOne(match);

        // Assert
        assertEquals(1, match.getPlayer1Games());
        assertEquals(0, match.getPlayer2Games());
    }

    @Test
    void should_increment_games_when_player_two_wins_a_game() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act
        winGameForPlayerTwo(match);

        // Assert
        assertEquals(0, match.getPlayer1Games());
        assertEquals(1, match.getPlayer2Games());
    }

    @Test
    void should_start_new_game_after_game_won() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act
        winGameForPlayerOne(match);

        // Assert
        assertEquals("Love-All", match.getGameScore());
    }

    @Test
    void should_detect_match_win_with_straight_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act
        for (int i = 0; i < 4; i++) winGameForPlayerOne(match);

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
        assertEquals("Games: 4-0 | Player 1 wins the match", match.getFullScore());
    }

    @Test
    void should_detect_match_win_for_player_two() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act
        for (int i = 0; i < 4; i++) winGameForPlayerTwo(match);

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 2", match.getMatchWinner());
    }

    @Test
    void should_not_be_over_at_4_3_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 3; i++) {
            winGameForPlayerOne(match);
            winGameForPlayerTwo(match);
        }

        // Act
        winGameForPlayerOne(match);

        // Assert
        assertFalse(match.isMatchOver());
        assertNull(match.getMatchWinner());
    }

    @Test
    void should_be_over_at_5_3_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 3; i++) {
            winGameForPlayerOne(match);
            winGameForPlayerTwo(match);
        }

        // Act
        winGameForPlayerOne(match);
        winGameForPlayerOne(match);

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
    }

    // --- Guards ---

    @Test
    void should_throw_when_player_one_scores_after_match_over() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) winGameForPlayerOne(match);

        // Act & Assert
        assertThrows(IllegalStateException.class, match::playerOneScores);
    }

    @Test
    void should_throw_when_player_two_scores_after_match_over() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) winGameForPlayerOne(match);

        // Act & Assert
        assertThrows(IllegalStateException.class, match::playerTwoScores);
    }

    @Test
    void should_return_match_over_for_game_score_when_match_finished() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) winGameForPlayerOne(match);

        // Assert
        assertEquals("Match over", match.getGameScore());
    }

    // --- fromScore ---

    static Stream<Arguments> validFromScoreProvider() {
        return Stream.of(
                Arguments.of(0, 0, 0, 0, "Games: 0-0 | Love-All"),
                Arguments.of(2, 1, 3, 2, "Games: 2-1 | Forty-Thirty"),
                Arguments.of(3, 3, 0, 0, "Games: 3-3 | Love-All"),
                Arguments.of(4, 2, 0, 0, "Games: 4-2 | Player 1 wins the match")
        );
    }

    @ParameterizedTest(name = "fromScore({0},{1},{2},{3}) → {4}")
    @MethodSource("validFromScoreProvider")
    void should_create_match_from_valid_score(int g1, int g2, int p1, int p2, String expected) {
        // Act
        TennisMatch match = TennisMatch.fromScore("Player 1", "Player 2", g1, g2, p1, p2);

        // Assert
        assertEquals(expected, match.getFullScore());
    }

    static Stream<Arguments> invalidFromScoreProvider() {
        return Stream.of(
                Arguments.of(7, 3, 0, 0),
                Arguments.of(4, 2, 1, 0)
        );
    }

    @ParameterizedTest(name = "fromScore({0},{1},{2},{3}) → throws")
    @MethodSource("invalidFromScoreProvider")
    void should_throw_on_invalid_from_score(int g1, int g2, int p1, int p2) {
        // Act & Assert
        assertThrows(InvalidScoreException.class,
                () -> TennisMatch.fromScore("P1", "P2", g1, g2, p1, p2));
    }

    // --- scorePoint(int) ---

    @Test
    void should_score_via_scorePoint_for_player_1() {
        // Arrange
        TennisMatch match = new TennisMatch("P1", "P2");

        // Act
        match.scorePoint(1);

        // Assert
        assertEquals("Fifteen-Love", match.getGameScore());
    }

    @Test
    void should_score_via_scorePoint_for_player_2() {
        // Arrange
        TennisMatch match = new TennisMatch("P1", "P2");

        // Act
        match.scorePoint(2);

        // Assert
        assertEquals("Love-Fifteen", match.getGameScore());
    }

    @Test
    void should_throw_scorePoint_for_invalid_player() {
        // Arrange
        TennisMatch match = new TennisMatch("P1", "P2");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> match.scorePoint(0));
    }

    @Test
    void should_throw_scorePoint_after_match_over() {
        // Arrange
        TennisMatch match = TennisMatch.fromScore("P1", "P2", 3, 2, 3, 0);
        match.scorePoint(1); // wins game 4-2, match over

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> match.scorePoint(1));
    }
}
