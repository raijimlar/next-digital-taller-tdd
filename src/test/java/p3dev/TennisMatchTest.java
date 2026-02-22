package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TennisMatchTest {

    @Test
    void should_start_with_zero_games() {
        // Arrange
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

        // Act: Player 1 wins a game (4 straight points)
        winGameForPlayerOne(match);

        // Assert
        assertEquals(1, match.getPlayer1Games());
        assertEquals(0, match.getPlayer2Games());
    }

    @Test
    void should_increment_games_when_player_two_wins_a_game() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: Player 2 wins a game (4 straight points)
        winGameForPlayerTwo(match);

        // Assert
        assertEquals(0, match.getPlayer1Games());
        assertEquals(1, match.getPlayer2Games());
    }

    @Test
    void should_start_new_game_after_game_won() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: Player 1 wins first game
        winGameForPlayerOne(match);

        // Assert: new game starts at Love-All
        assertEquals("Love-All", match.getGameScore());
    }

    // --- Match winning ---

    @Test
    void should_detect_match_win_with_straight_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: Player 1 wins 4 games straight
        for (int i = 0; i < 4; i++) {
            winGameForPlayerOne(match);
        }

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
        assertEquals("Games: 4-0 | Player 1 wins the match", match.getFullScore());
    }

    @Test
    void should_detect_match_win_for_player_two() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: Player 2 wins 4 games
        for (int i = 0; i < 4; i++) {
            winGameForPlayerTwo(match);
        }

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 2", match.getMatchWinner());
    }

    @Test
    void should_not_be_over_at_4_3_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: 4-3 (not enough lead)
        for (int i = 0; i < 3; i++) {
            winGameForPlayerOne(match);
            winGameForPlayerTwo(match);
        }
        winGameForPlayerOne(match); // 4-3

        // Assert
        assertFalse(match.isMatchOver());
        assertNull(match.getMatchWinner());
    }

    @Test
    void should_be_over_at_5_3_games() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        // Act: both reach 3, then Player 1 wins 2 more
        for (int i = 0; i < 3; i++) {
            winGameForPlayerOne(match);
            winGameForPlayerTwo(match);
        }
        winGameForPlayerOne(match); // 4-3
        winGameForPlayerOne(match); // 5-3

        // Assert
        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
    }

    // --- Guards ---

    @Test
    void should_throw_when_scoring_after_match_over() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) {
            winGameForPlayerOne(match);
        }

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> match.playerOneScores());
        assertThrows(IllegalStateException.class, () -> match.playerTwoScores());
    }

    @Test
    void should_return_match_over_for_game_score_when_match_finished() {
        // Arrange
        TennisMatch match = new TennisMatch("Player 1", "Player 2");
        for (int i = 0; i < 4; i++) {
            winGameForPlayerOne(match);
        }

        // Assert
        assertEquals("Match over", match.getGameScore());
    }

    // --- Factory method fromScore ---

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
        assertNotNull(match);
        assertEquals(expected, match.getFullScore());
    }

    @Test
    void should_throw_on_invalid_fromScore() {
        // 7-3 in games is impossible
        assertThrows(InvalidScoreException.class,
                () -> TennisMatch.fromScore("P1", "P2", 7, 3, 0, 0));

        // Match over but game in progress
        assertThrows(InvalidScoreException.class,
                () -> TennisMatch.fromScore("P1", "P2", 4, 2, 1, 0));
    }

    // --- Helpers ---

    private void winGameForPlayerOne(TennisMatch match) {
        for (int i = 0; i < 4; i++) {
            match.playerOneScores();
        }
    }

    private void winGameForPlayerTwo(TennisMatch match) {
        for (int i = 0; i < 4; i++) {
            match.playerTwoScores();
        }
    }
}
