package p3dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameFlowIntegrationTest {

    @Test
    void should_transition_through_all_states_in_a_realistic_game() {
        TennisGame game = new TennisGame("Player 1", "Player 2");

        assertEquals("Love-All", game.getScore());

        game.playerOneScores();
        assertEquals("Fifteen-Love", game.getScore());

        game.playerTwoScores();
        assertEquals("Fifteen-All", game.getScore());

        game.playerOneScores();
        assertEquals("Thirty-Fifteen", game.getScore());
        game.playerOneScores();
        assertEquals("Forty-Fifteen", game.getScore());

        game.playerTwoScores();
        assertEquals("Forty-Thirty", game.getScore());
        game.playerTwoScores();
        assertEquals("Deuce", game.getScore());

        game.playerOneScores();
        assertEquals("Advantage Player 1", game.getScore());

        game.playerTwoScores();
        assertEquals("Deuce", game.getScore());

        game.playerTwoScores();
        assertEquals("Advantage Player 2", game.getScore());

        game.playerTwoScores();
        assertEquals("Player 2 wins", game.getScore());
    }

    @Test
    void should_handle_straight_win_for_player_one() {
        TennisGame game = new TennisGame("Player 1", "Player 2");

        game.playerOneScores();
        game.playerOneScores();
        game.playerOneScores();
        game.playerOneScores();

        assertEquals("Player 1 wins", game.getScore());
    }

    @Test
    void should_handle_straight_win_for_player_two() {
        TennisGame game = new TennisGame("Player 1", "Player 2");

        game.playerTwoScores();
        game.playerTwoScores();
        game.playerTwoScores();
        game.playerTwoScores();
        assertEquals("Player 2 wins", game.getScore());
    }

    @Test
    void should_handle_advantage_lost_back_to_deuce() {
        TennisGame game = new TennisGame("Player 1", "Player 2");

        for (int i = 0; i < 3; i++) {
            game.playerOneScores();
            game.playerTwoScores();
        }
        assertEquals("Deuce", game.getScore());

        game.playerOneScores();
        assertEquals("Advantage Player 1", game.getScore());

        game.playerTwoScores();
        assertEquals("Deuce", game.getScore());
    }
}
