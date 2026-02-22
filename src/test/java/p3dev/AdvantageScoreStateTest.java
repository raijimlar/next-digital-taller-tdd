package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AdvantageScoreStateTest {

    private static final AdvantageScoreState STATE = new AdvantageScoreState();

    private static Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }

    static Stream<Arguments> player1AdvantageSituations() {
        return Stream.of(
            Arguments.of("Alice",  "Bob",    4, 3, "Advantage Alice"),
            Arguments.of("Carlos", "Diana",  5, 4, "Advantage Carlos"),
            Arguments.of("Eve",    "Frank",  6, 5, "Advantage Eve")
        );
    }

    @ParameterizedTest(name = "[{index}] {0} leads {1} ({2}-{3}) => {4}")
    @MethodSource("player1AdvantageSituations")
    void getScore_player1HasAdvantage_returnsAdvantagePlayer1(
            String name1, String name2, int p1Points, int p2Points, String expected) {
        Player player1 = playerWithPoints(name1, p1Points);
        Player player2 = playerWithPoints(name2, p2Points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    static Stream<Arguments> player2AdvantageSituations() {
        return Stream.of(
            Arguments.of("Alice",  "Bob",    3, 4, "Advantage Bob"),
            Arguments.of("Carlos", "Diana",  4, 5, "Advantage Diana"),
            Arguments.of("Eve",    "Frank",  5, 6, "Advantage Frank")
        );
    }

    @ParameterizedTest(name = "[{index}] {1} leads {0} ({3}-{2}) => {4}")
    @MethodSource("player2AdvantageSituations")
    void getScore_player2HasAdvantage_returnsAdvantagePlayer2(
            String name1, String name2, int p1Points, int p2Points, String expected) {
        Player player1 = playerWithPoints(name1, p1Points);
        Player player2 = playerWithPoints(name2, p2Points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    @Test
    void isTerminal_returnsFalse() {
        assertFalse(STATE.isTerminal());
    }
}
