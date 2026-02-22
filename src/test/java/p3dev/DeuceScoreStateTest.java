package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DeuceScoreStateTest {

    private static final DeuceScoreState STATE = new DeuceScoreState();

    private static Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }

    static Stream<Arguments> deuceSituations() {
        return Stream.of(
            Arguments.of(3, 3),
            Arguments.of(4, 4),
            Arguments.of(5, 5)
        );
    }

    @ParameterizedTest(name = "[{index}] {0}-{1} pts => Deuce")
    @MethodSource("deuceSituations")
    void getScore_equalPointsAboveThirty_returnsDeuce(int p1Points, int p2Points) {
        Player player1 = playerWithPoints("Alice", p1Points);
        Player player2 = playerWithPoints("Bob", p2Points);
        assertEquals("Deuce", STATE.getScore(player1, player2));
    }

    @Test
    void isTerminal_returnsFalse() {
        assertFalse(STATE.isTerminal());
    }
}
