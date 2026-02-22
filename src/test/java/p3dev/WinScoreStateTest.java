package p3dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinScoreStateTest {

    private final WinScoreState state = new WinScoreState();

    @Test
    void should_return_player_one_wins_when_player_one_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 4);
        Player p2 = playerWithPoints("Player 2", 0);

        // Act & Assert
        assertEquals("Player 1 wins", state.getScore(p1, p2));
    }

    @Test
    void should_return_player_two_wins_when_player_two_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 0);
        Player p2 = playerWithPoints("Player 2", 4);

        // Act & Assert
        assertEquals("Player 2 wins", state.getScore(p1, p2));
    }

    @Test
    void should_use_player_name_in_win_message() {
        // Arrange
        Player p1 = playerWithPoints("Nadal", 5);
        Player p2 = playerWithPoints("Federer", 3);

        // Act & Assert
        assertEquals("Nadal wins", state.getScore(p1, p2));
    }

    @Test
    void should_be_terminal() {
        assertTrue(state.isTerminal());
    }

    private Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }
}
