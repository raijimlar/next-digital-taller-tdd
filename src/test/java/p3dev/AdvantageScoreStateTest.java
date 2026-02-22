package p3dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdvantageScoreStateTest {

    private final AdvantageScoreState state = new AdvantageScoreState();

    @Test
    void should_return_advantage_player_one_when_player_one_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 4);
        Player p2 = playerWithPoints("Player 2", 3);

        // Act & Assert
        assertEquals("Advantage Player 1", state.getScore(p1, p2));
    }

    @Test
    void should_return_advantage_player_two_when_player_two_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 3);
        Player p2 = playerWithPoints("Player 2", 4);

        // Act & Assert
        assertEquals("Advantage Player 2", state.getScore(p1, p2));
    }

    @Test
    void should_use_player_name_in_advantage_message() {
        // Arrange
        Player p1 = playerWithPoints("Nadal", 5);
        Player p2 = playerWithPoints("Federer", 4);

        // Act & Assert
        assertEquals("Advantage Nadal", state.getScore(p1, p2));
    }

    @Test
    void should_not_be_terminal() {
        assertFalse(state.isTerminal());
    }

    private Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }
}
