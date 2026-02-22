package p3dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchFlowIntegrationTest {

    @Test
    void should_play_complete_match_with_player_one_winning() {
        // Arrange
        TennisMatch match = new TennisMatch("Nadal", "Federer");

        // Assert initial state
        assertEquals("Games: 0-0 | Love-All", match.getFullScore());

        scorePoints(match, 4, 0);
        assertEquals(1, match.getPlayer1Games());
        assertEquals("Games: 1-0 | Love-All", match.getFullScore());

        scorePoints(match, 0, 4);
        assertEquals(1, match.getPlayer2Games());
        assertEquals("Games: 1-1 | Love-All", match.getFullScore());

        scorePoints(match, 3, 3);
        assertEquals("Games: 1-1 | Deuce", match.getFullScore());
        match.playerOneScores();
        assertEquals("Games: 1-1 | Advantage Nadal", match.getFullScore());
        match.playerOneScores();
        assertEquals("Games: 2-1 | Love-All", match.getFullScore());

        scorePoints(match, 4, 0);
        assertEquals("Games: 3-1 | Love-All", match.getFullScore());
        scorePoints(match, 4, 0);

        assertTrue(match.isMatchOver());
        assertEquals("Nadal", match.getMatchWinner());
        assertEquals("Games: 4-1 | Nadal wins the match", match.getFullScore());
    }

    @Test
    void should_play_tight_match_with_extended_games() {

        TennisMatch match = new TennisMatch("Player 1", "Player 2");

        for (int i = 0; i < 3; i++) {
            scorePoints(match, 4, 0);
            scorePoints(match, 0, 4);
        }
        assertEquals("Games: 3-3 | Love-All", match.getFullScore());
        assertFalse(match.isMatchOver());

        scorePoints(match, 4, 0);
        assertEquals("Games: 4-3 | Love-All", match.getFullScore());
        assertFalse(match.isMatchOver());

        scorePoints(match, 0, 4);
        assertEquals("Games: 4-4 | Love-All", match.getFullScore());
        assertFalse(match.isMatchOver());

        scorePoints(match, 4, 0);
        assertFalse(match.isMatchOver());

        scorePoints(match, 4, 0);
        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
    }

    @Test
    void should_create_match_from_existing_score_and_continue() {
        TennisMatch match = TennisMatch.fromScore("Player 1", "Player 2", 3, 2, 2, 1);
        assertNotNull(match);
        assertEquals("Games: 3-2 | Thirty-Fifteen", match.getFullScore());

        match.playerOneScores();
        match.playerOneScores();

        assertTrue(match.isMatchOver());
        assertEquals("Player 1", match.getMatchWinner());
    }

    @Test
    void should_reject_impossible_score_with_7_3() {
        assertThrows(InvalidScoreException.class,
                () -> TennisMatch.fromScore("P1", "P2", 7, 3, 0, 0));
    }

    private void scorePoints(TennisMatch match, int p1, int p2) {
        for (int i = 0; i < p1; i++) match.playerOneScores();
        for (int i = 0; i < p2; i++) match.playerTwoScores();
    }
}
