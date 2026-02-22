package p3dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeuceScoreStateTest {

    private final DeuceScoreState state = new DeuceScoreState();

    @Test
    void should_return_deuce_regardless_of_player_names() {
        // Arrange
        Player p1 = new Player("Nadal");
        Player p2 = new Player("Federer");

        // Act & Assert
        assertEquals("Deuce", state.getScore(p1, p2));
    }

    @Test
    void should_not_be_terminal() {
        assertFalse(state.isTerminal());
    }
}
